package com.example.vuesever.controller;

import com.example.vuesever.bean.Collection;
import com.example.vuesever.bean.Constants;
import com.example.vuesever.service.CollectionService;
import com.example.vuesever.service.SongService;
import com.example.vuesever.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectionController {

    @Autowired
    private CollectionService collectionService;
    @Autowired
    private SongService songService;

    @PostMapping("/validateCollectionId")
    public HttpResult validateCollectionId(@RequestBody Collection collection){
        if (collectionService.validateCollectionId(collection)){
            return HttpResult.success("验证成功");
        }
        return HttpResult.failure("请勿重复收藏", Constants.KZ_COLLECTION_HAS_ERROR);
    }

    @PostMapping("/addCollection")
    public HttpResult addCollection(@RequestBody Collection collection){
        if (collectionService.save(collection)){
            return HttpResult.success("添加成功");
        }
        return HttpResult.failure("验证失败", Constants.KZ_COLLECTION_ADD_ERROR);
    }

    @PostMapping("/collection")
    public HttpResult getCollectionMusicList(@RequestBody Collection collection){
        return HttpResult.success("查询成功",songService.getCollectionList(collection.getUserId()));
    }

    @PostMapping("/removeCollection")
    public HttpResult removeCollection(@RequestBody Collection collection){
        if (collectionService.removeCollection(collection)){
            return HttpResult.success("删除成功");
        }
        return HttpResult.failure("删除失败",Constants.KZ_COLLECTION_REMOVE_ERROR);
    }
}
