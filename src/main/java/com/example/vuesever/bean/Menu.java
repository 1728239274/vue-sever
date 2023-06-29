package com.example.vuesever.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String title;
    private Integer weight;
    private String path;
    private String icon;
    private Integer status;
    private Integer rid;
    private Date createTime;
    private Date updateTime;

}
