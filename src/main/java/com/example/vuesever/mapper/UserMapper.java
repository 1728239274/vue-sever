package com.example.vuesever.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vuesever.bean.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select distinct p.keyword from permission p " +
            "join role_permission rp on rp.permission_id = p.id " +
            "join role r on r.id = rp.role_id " +
            "join user_role ur on ur.role_id = r.id " +
            "where ur.user_id = #{id}")
    List<String> getPermissionByUserId(Integer id);

    @Select("select r.keyword from role r " +
            "join user_role ur on ur.role_id = r.id " +
            "where ur.user_id = #{id}")
    List<String> getRoleByUserId(Integer id);

    @Update("update user set password = #{new_password} where username = #{username}")
    Boolean updatePassword(String username,String new_password);
}
