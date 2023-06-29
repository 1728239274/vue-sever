package com.example.vuesever.controller;

import com.example.vuesever.bean.vo.PlutocratVo;
import com.example.vuesever.service.PlutocratService;
import com.example.vuesever.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasRole('ADMIN')")
public class PlutocratController {

    @Autowired
    private PlutocratService plutocratService;

    @PostMapping("/plutocrat2022")
    public HttpResult getPlutocratListByPageFind(@RequestBody PlutocratVo plutocratVo){
        return HttpResult.success("查询成功",plutocratService.getPagePlutocrat(plutocratVo));
    }

}
