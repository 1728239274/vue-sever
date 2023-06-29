package com.example.vuesever.controller;

import com.example.vuesever.bean.Constants;
import com.example.vuesever.bean.User;
import com.example.vuesever.bean.vo.UserVo;
import com.example.vuesever.service.UserService;
import com.example.vuesever.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserInfo")
    public HttpResult getUserInfo(){
        return HttpResult.success("查询成功",userService.getUserInfo());
    }

    @PostMapping("/updatePassword")
    public HttpResult updatePassword(@RequestBody UserVo userVo){
        if (userService.updatePassword(userVo)){
            return HttpResult.success("修改成功");
        }
        return HttpResult.failure("旧密码错误", Constants.OLD_PASSWORD_ERROR);
    }

    @GetMapping("/users")
    public HttpResult getUserList(){
        return HttpResult.success("查询成功",userService.list());
    }

    @DeleteMapping("/user/{id}")
    public HttpResult deleteUserById(@PathVariable Integer id){
        if(userService.removeById(id)){
            return HttpResult.success("删除成功");
        }
        return HttpResult.failure("删除失败",Constants.DELETE_USER_ERROR);
    }

    //注销登录
    @PostMapping("/logout")
    public HttpResult logout(){
        userService.logout();
        return HttpResult.success("注销成功");
    }

    @PostMapping("/updateUserInfo")
    public HttpResult updateUserInfo(@RequestBody User user){
        if(userService.updateUserInfo(user)){
            return HttpResult.success("修改成功");
        }
        return HttpResult.failure("修改失败",Constants.UPDATE_USER_ERROR);
    }

    @PostMapping("/uploadAvatar")
    public HttpResult uploadAvatar(@RequestParam("imgFile") MultipartFile imgFile){
        String s = userService.uploadAvatar(imgFile);
        if(s == null){
            return HttpResult.failure("上传失败", Constants.UPLOAD_USER_AVATAR_ERR);
        }
        return HttpResult.success("上传成功",s);
    }

    //修改
    @PostMapping("/updateUser")
    public HttpResult updateUser(@RequestBody User user){
        if(userService.updateById(user)){
            return HttpResult.success("修改成功");
        }
        return HttpResult.failure("修改失败",Constants.UPDATE_USER_ERROR);
    }
}
