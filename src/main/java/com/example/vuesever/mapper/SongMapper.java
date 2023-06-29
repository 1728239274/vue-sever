package com.example.vuesever.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vuesever.bean.Song;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface SongMapper extends BaseMapper<Song> {

        @Update("ALTER TABLE song AUTO_INCREMENT = 1")
        int updateAUTO_INCREMENT();

}
