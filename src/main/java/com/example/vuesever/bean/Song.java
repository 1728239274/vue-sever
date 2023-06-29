package com.example.vuesever.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("song")
public class Song {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String artist;
    private String url;
    private String cover;
}
