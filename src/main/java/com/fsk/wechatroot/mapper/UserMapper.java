package com.fsk.wechatroot.mapper;

import com.fsk.wechatroot.dao.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Author: fsk
 * Date: 2022/9/3 12:06
 * FileName: userMapper
 * Description: 用户类的数据获取
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> getAll();

    @Select("select username from user")
    List<String> getUsername();

    @Select("select * from user where username=#{username}")
    List<User> selectForUsername(String username);

    @Select("select * from user where ck=#{ck}")
    User selectByCk(String ck);

    @Insert("insert user value (id,#{username},#{jdname},#{ck},#{time},0)")
    int insertUser(User user);

    @Select("select * from user where username=#{username} and jdname=#{jdname}")
    User selectByTwoName(String username,String jdname);

    @Update("update user set ck=#{ck} where jdname=#{jdname} and username=#{username}")
    int updateToCk(String ck,String jdname,String username);


}
