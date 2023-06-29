package com.example.vuesever.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vuesever.bean.Constants;
import com.example.vuesever.bean.LoginUser;
import com.example.vuesever.bean.User;
import com.example.vuesever.bean.vo.UserVo;
import com.example.vuesever.exception.UserCodeException;
import com.example.vuesever.mapper.UserMapper;
import com.example.vuesever.utils.EmailAccountHelper;
import com.example.vuesever.utils.GrantedAuthorityPlus;
import com.example.vuesever.utils.QiNiuUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User>{

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginUser getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser;
    }

    public void initUserLoginRedis(LoginUser loginUser){

        List<String> permissions = userMapper.getPermissionByUserId(loginUser.getUser().getId());
        permissions.addAll(userMapper.getRoleByUserId(loginUser.getUser().getId()));
        List<GrantedAuthorityPlus> authorityPluses = permissions.stream()
                .map(GrantedAuthorityPlus::new).collect(Collectors.toList());
        loginUser.setAuthorities(authorityPluses);
        int id = loginUser.getUser().getId();
        User user = loginUser.getUser();
        user.setAvatar(Constants.qiNiuCDN+user.getAvatar());
        loginUser.setUser(user);
        try {
            redisTemplate.opsForValue().set(Constants.redisLoginKey+id,objectMapper.writeValueAsString(loginUser));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("存入json错误");
        }
    }

    @Override
    public List<User> list() {
        return userMapper.selectList(null);
    }

    public String getImageCode(String id){
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        //使用redis工具保存验证码功能
        redisTemplate.boundValueOps("code"+id).set(captcha.getCode(),5, TimeUnit.MINUTES);
        return captcha.getImageBase64Data();
    }

    public Object login(String codeId, UserVo userVo) throws UserCodeException {
        String code = redisTemplate.opsForValue().get("code"+codeId);
        if (!StringUtils.hasText(code)) throw new UserCodeException("验证码失效");
        if(!userVo.getCode().equals(code)) throw new UserCodeException("验证码错误");
//        redisTemplate.delete("code"+codeId);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userVo.getUsername(),userVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(authenticate == null) return null;
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        //authenticate存入redis
        initUserLoginRedis(loginUser);

        //使用userid生成token
        int userId= loginUser.getUser().getId();
        Map<String, Object> map = new HashMap<>();
        map.put("uid", userId);
        map.put("expire_time", System.currentTimeMillis());
        String jwt = JWTUtil.createToken(map, Constants.tokenKey);
        //把token响应给前端
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",jwt);
        tokenMap.put("username",loginUser.getUsername());
        tokenMap.put("avatar",loginUser.getUser().getAvatar());
        return tokenMap;
    }

    public void logout() {
        int id = getLoginUser().getUser().getId();
        redisTemplate.delete(Constants.redisLoginKey + id);
    }

    public void sendEmail(String email){
        String randomCode = String.valueOf(RandomUtil.randomInt(100000, 1000000));
        redisTemplate.boundValueOps(email).set(randomCode, Constants.REGISTER_EMAIL_CODE_TIME,TimeUnit.MINUTES);
        String txt = "尊敬的用户您好：\n\n你的验证码为"+randomCode+"（请您妥善保管您的验证码，切勿告知他人），请在页面中输入完成验证。\n\n安全提示：为保障您的帐户安全，请在 5 分钟内完成验证，否则验证码将自动失效。";
        MailUtil.send(EmailAccountHelper.createMailAccount(),email,"酷猪注册验证码",txt,false);
    }

    public boolean validateEmail(UserVo userVo) {
        String code = redisTemplate.opsForValue().get(userVo.getEmail());
        if (!StringUtils.hasText(code)) throw new UserCodeException("验证码失效");
        return Objects.equals(userVo.getCode(),code);
    }

    public boolean validateUsername(String username){
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return user == null;
    }


    public boolean addUser(UserVo userVo) {
        if (!validateEmail(userVo)) throw new UserCodeException("验证码错误");
        redisTemplate.delete(userVo.getEmail());
        String encode = passwordEncoder.encode(userVo.getPassword());
        userVo.setPassword(encode);
        return userMapper.insert(userVo) > 0;
    }

    public boolean updatePassword(UserVo userVo) {
        LoginUser loginUser = getLoginUser();
        boolean matches = passwordEncoder.matches(userVo.getPassword(), loginUser.getPassword());
        if (!matches) return false;
        String newPassword = passwordEncoder.encode(userVo.getNewPassword());
        if (userMapper.updatePassword(loginUser.getUsername(),newPassword)){
            initUserLoginRedis(loginUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean removeById(Serializable id) {
        return userMapper.deleteById(id) > 0;
    }

    public Map getUserInfo() {
        User user = getLoginUser().getUser();
        Map<String,String> map = new HashMap<>();
        map.put("phone",user.getPhone());
        map.put("email",user.getEmail());
        map.put("address",user.getAddress());
        return map;
    }

    public boolean updateUserInfo(User user) {
        LoginUser loginUser = getLoginUser();
        user.setId(loginUser.getUser().getId());
        if (userMapper.updateById(user) > 0){
            initUserLoginRedis(loginUser);
            return true;
        }
        return false;
    }

    public String uploadAvatar(MultipartFile imgFile) {
        try{
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf - 1);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = "img/"+ UUID.randomUUID() + suffix;
            QiNiuUtils.upload2QiNiu(imgFile.getBytes(),fileName);

//            redisTemplate.opsForSet().add(Constants.SET_MEAL_PIC_RESOURCES,fileName);
            User user = new User();
            user.setAvatar(fileName);
            if (!updateUserInfo(user)) return null;

            return Constants.qiNiuCDN+fileName;
        }catch (Exception e){
            return null;
        }
    }
}
