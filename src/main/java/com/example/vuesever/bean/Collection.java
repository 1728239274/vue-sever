package com.example.vuesever.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("collection")
public class Collection {
    private Integer userId;
    private Integer songId;
}
