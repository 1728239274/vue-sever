package com.example.vuesever.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.vuesever.bean.Collection;
import com.example.vuesever.bean.User;
import com.example.vuesever.mapper.CollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionService extends BaseService<Collection>{

    @Autowired
    private CollectionMapper collectionMapper;

    public boolean validateCollectionId(Collection collection){
        Collection col = collectionMapper.selectOne(new QueryWrapper<Collection>().eq("user_id",collection.getUserId()).eq("song_id",collection.getSongId()));
        return col == null;
    }

    @Override
    public boolean save(Collection collection) {
        return collectionMapper.insert(collection) > 0;
    }

    public boolean removeCollection(Collection collection){
        return collectionMapper.delete(new QueryWrapper<Collection>().eq("user_id",collection.getUserId()).eq("song_id",collection.getSongId())) > 0;
    }



}
