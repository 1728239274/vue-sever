package com.example.vuesever.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vuesever.bean.Plutocrat;
import com.example.vuesever.bean.vo.PlutocratVo;
import com.example.vuesever.mapper.PlutocratMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlutocratService extends BaseService<Plutocrat> {

    @Autowired
    private PlutocratMapper plutocratMapper;

    public PlutocratVo getPagePlutocrat(PlutocratVo plutocratVo){
        return plutocratMapper.selectPage(
                plutocratVo,new QueryWrapper<Plutocrat>()
                        .like(plutocratVo.getNameEn() != null,"name_en",plutocratVo.getNameEn())
                        .like(plutocratVo.getNameCh() != null,"name_ch",plutocratVo.getNameCh())
                        .like(plutocratVo.getWealthSource() != null,"wealth_source",plutocratVo.getWealthSource())
                        .like(plutocratVo.getNation() != null,"nation",plutocratVo.getNation())
        );
    }

}
