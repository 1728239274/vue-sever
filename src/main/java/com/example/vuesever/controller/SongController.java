package com.example.vuesever.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.vuesever.bean.Song;
import com.example.vuesever.bean.vo.SongVo;
import com.example.vuesever.service.SongService;
import com.example.vuesever.utils.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@PreAuthorize("hasRole('USER')")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/songs")
    public Page<Song> getSongListByPage(Page page){
        return songService.page(page);
    }

    @PostMapping("/songs")
    public HttpResult getSongListByPageFind(@RequestBody SongVo songVo){
        return HttpResult.success("查询成功",songService.getPageSongs(songVo));
    }

    @GetMapping("/song/{id}")
    public HttpResult getSongById(@PathVariable Integer id){
        return HttpResult.success("查询成功",new Object());
    }

    @DeleteMapping("/song/{id}")
    public HttpResult deleteSongById(@PathVariable Integer id){
        if(songService.removeById(id)){
            return HttpResult.success("删除成功");
        }
        return HttpResult.failure("删除失败",1600);
    }

    @PostMapping("/song")
    public HttpResult addSong(@RequestBody Song song){
        if(songService.save(song)){
            return HttpResult.success("添加成功");
        }
        return HttpResult.failure("添加失败",1601);
    }

    @PutMapping("/song")
    public HttpResult updateSong(@RequestBody Song song){
        if(songService.updateById(song)){
            return HttpResult.success("修改成功");
        }
        return HttpResult.failure("修改失败",1603);
    }

}
