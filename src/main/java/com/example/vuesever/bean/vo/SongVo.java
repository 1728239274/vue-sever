package com.example.vuesever.bean.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.vuesever.bean.Song;
import lombok.Data;

@Data
public class SongVo extends Page<Song>{
    private String name;
    private String artist;
}
