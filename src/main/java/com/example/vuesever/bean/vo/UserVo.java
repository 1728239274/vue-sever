package com.example.vuesever.bean.vo;

import com.example.vuesever.bean.User;
import lombok.Data;

@Data
public class UserVo extends User {
    private String code;
    private String password;
    private String newPassword;
}
