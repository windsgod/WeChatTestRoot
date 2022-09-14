package com.fsk.wechatroot.mapper;

import com.fsk.wechatroot.dao.Config;
import com.fsk.wechatroot.dao.test;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Author: fsk
 * Date: 2022/9/3 10:04
 * FileName: testMapper
 * Description: 测试数据库方法接口
 */
@Mapper
public interface ConfigMapper {

    @Select("select * from config")
    Config getAll();

}
