package com.example.vuesever.handler;


import com.example.vuesever.bean.Constants;
import com.example.vuesever.exception.UserCodeException;
import com.example.vuesever.utils.HttpResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        HttpResult httpResult;
        if (exception instanceof InsufficientAuthenticationException) {
            httpResult = HttpResult.failure("用户未登录", Constants.USER_NO_LOGIN_ERROR);
        }else if (exception instanceof BadCredentialsException) {
            httpResult = HttpResult.failure("用户名或密码错误", Constants.LOGIN_USER_ERROR);
        }else if (exception instanceof UserCodeException) {
            httpResult = HttpResult.failure(exception.getMessage(), Constants.LOGIN_CODE_ERROR);
        }else if (exception instanceof LockedException) {
            httpResult = HttpResult.failure("用户账号已被锁定，请联系平台客服", Constants.LOGIN_USER_LOCKED_ERROR);
        } else {
            httpResult = HttpResult.failure("其他错误", Constants.LOGIN_ERROR);
        }
        response.getWriter().write(objectMapper.writeValueAsString(httpResult));
    }
}
