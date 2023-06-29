package com.example.vuesever.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("plutocrat2022")
public class Plutocrat {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer ranking;
    private String nameEn;
    private String nameCh;
    private Integer wealth;
    private String wealthSource;
    private String nation;
    private Integer age;
}
