package com.example.vuesever.controller;

import com.example.vuesever.service.MenuService;
import com.example.vuesever.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('USER')")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public HttpResult getMenu(){
        return HttpResult.success("获取成功",menuService.getMenu());
    }
}
