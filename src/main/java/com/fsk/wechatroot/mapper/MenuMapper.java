package com.fsk.wechatroot.mapper;

import com.fsk.wechatroot.dao.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: fsk
 * Date: 2022/9/3 13:29
 * FileName: MenuMapper
 * Description: 菜单数据获取接口
 */
@Mapper
public interface MenuMapper {


    @Select("select menuname from menu where namegroup<=(select usergroup from user where username=#{username})")
    List<String> SelectMenu(String username);



}
