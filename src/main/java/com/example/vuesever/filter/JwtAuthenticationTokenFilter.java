package com.example.vuesever.filter;

import cn.hutool.jwt.JWTUtil;
import com.example.vuesever.bean.Constants;
import com.example.vuesever.bean.LoginUser;
import com.example.vuesever.utils.HttpResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("token");
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        try {
            if (!JWTUtil.verify(token, Constants.tokenKey)) throw new RuntimeException();
        } catch (RuntimeException e) {
            HttpResult httpResult = HttpResult.failure("token非法", Constants.LOGIN_TOKEN_ERROR);
            response.getWriter().write(objectMapper.writeValueAsString(httpResult));
            return;
        }
        String uid = JWTUtil.parseToken(token).getPayload().getClaim("uid").toString();
        String loginUserJson = stringRedisTemplate.opsForValue().get(Constants.redisLoginKey + uid);

        if (!StringUtils.hasText(loginUserJson)) {
            HttpResult httpResult = HttpResult.failure("登录过期，请重新登录", Constants.LOGIN_OVERDUE_ERROR);
            response.getWriter().write(objectMapper.writeValueAsString(httpResult));
            return;
        }
        LoginUser loginUser = objectMapper.readValue(loginUserJson,LoginUser.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request,response);
    }
}
