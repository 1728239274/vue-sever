package com.example.vuesever.utils;

import lombok.Data;

@Data
public class HttpResult {
    private Integer code;
    private String msg;
    private Object data;

    public static HttpResult success(String msg,Object data){
        HttpResult result = new HttpResult();
        result.setMsg(msg);
        result.setData(data);
        result.setCode(600);
        return result;
    }

    public static HttpResult success(String msg){
        HttpResult result = new HttpResult();
        result.setMsg(msg);
        result.setData(null);
        result.setCode(600);
        return result;
    }

    public static HttpResult failure(String msg,Integer code){
        HttpResult result = new HttpResult();
        result.setMsg(msg);
        result.setData(null);
        result.setCode(code);
        return result;
    }
}
