package com.example.vuesever.bean.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.vuesever.bean.Plutocrat;
import lombok.Data;

@Data
public class PlutocratVo extends Page<Plutocrat>{
    private String nameEn;
    private String nameCh;
    private String wealthSource;
    private String nation;
}
