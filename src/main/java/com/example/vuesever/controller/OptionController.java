package com.example.vuesever.controller;

import com.example.vuesever.bean.Constants;
import com.example.vuesever.bean.vo.UserVo;
import com.example.vuesever.exception.UserCodeException;
import com.example.vuesever.service.UserService;
import com.example.vuesever.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/user")
public class OptionController {


    @Autowired
    private UserService userService;

    //用户登录
    @PostMapping("/login")
    public HttpResult login(@RequestBody UserVo userVo,HttpSession session) throws UserCodeException {
        return HttpResult.success("登录成功",userService.login(session.getId(),userVo));
    }

    //验证码获取
    @PostMapping("/captcha")
    public HttpResult getCaptchaImage(HttpSession session){
        return HttpResult.success(userService.getImageCode(session.getId()));
    }

    //用户注册
    //发送邮件验证码
    @PostMapping("/sendEmail")
    public HttpResult sendEmail(@RequestBody UserVo userVo){
        userService.sendEmail(userVo.getEmail());
        return HttpResult.success("成功发送邮箱验证码");
    }

    @PostMapping("/validateEmail")
    public HttpResult validateEmail(@RequestBody UserVo userVo){
        if (userService.validateEmail(userVo)){
            return HttpResult.success("验证成功");
        }
        return HttpResult.failure("验证码错误",Constants.REGISTER_CODE_ERROR);
    }

    //验证用户名
    @PostMapping("/validateUsername")
    public HttpResult validateUsername(@RequestBody UserVo userVo){
        if (userService.validateUsername(userVo.getUsername())){
            return HttpResult.success("用户不存在");
        }
        return HttpResult.failure("用户名已存在",Constants.REGISTER_USERNAME_HAS);
    }

    @PostMapping("/addUser")
    public HttpResult addUser(@RequestBody UserVo userVo){
        if (userService.addUser(userVo)){
            return HttpResult.success("注册成功");
        }
        return HttpResult.failure("注册失败",Constants.REGISTER_USERNAME_HAS);
    }

}
