package com.example.vuesever.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.vuesever.bean.Song;
import com.example.vuesever.bean.vo.SongVo;
import com.example.vuesever.mapper.SongMapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class SongService extends BaseService<Song> {

    //查询所有音乐
    @Autowired
    private SongMapper songMapper;

    //酷猪前台
    @Override
    public List<Song> list() {
        return songMapper.selectList(null);
    }
    public List<Song> getSearchList(SongVo songVo){
        return songMapper.selectList(new QueryWrapper<Song>().like("name",songVo.getName()).or().like("artist",songVo.getName()));
    }
    public List<Song> getRecommendList(){
        return songMapper.selectList(new QueryWrapper<Song>().inSql("id","select id from recommend"));
    }
    public List<Song> getCollectionList(int userId){
        return songMapper.selectList(new QueryWrapper<Song>().inSql("id","select song_id from collection where user_id = "+userId+""));
    }

    //分页查询
    @Override
    public <E extends IPage<Song>> E page(E page) {
        return songMapper.selectPage(page,null);
    }

    //添加音乐
    @Override
    public boolean save(Song song) {
        return songMapper.insert(song) > 0;
    }

    //条件查询
    public SongVo getPageSongs(SongVo songVo){
        return songMapper.selectPage(
                songVo,new QueryWrapper<Song>()
                        .like(songVo.getName() != null,"name",songVo.getName())
                        .like(songVo.getArtist() != null,"artist",songVo.getArtist())
        );
    }


    //通过id查询
    @Override
    public Song getById(Serializable id) {
        return songMapper.selectById(id);
    }

    //修改
    @Override
    public boolean updateById(Song song) {
        return songMapper.updateById(song) > 0;
    }

    //删除
    @Override
    public boolean removeById(Serializable id) {
        if (songMapper.deleteById(id) > 0){
            songMapper.updateAUTO_INCREMENT();
            return true;
        }
        return false;
    }
}
