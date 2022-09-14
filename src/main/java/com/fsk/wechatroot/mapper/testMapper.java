package com.fsk.wechatroot.mapper;

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
public interface testMapper {

    @Select("select * from test")
    List<test> getAll();

    @Insert("insert test value (#{id},#{name})")
    int insert_test(test t);

    @Delete("delete from test where id=#{id}")
    int delete_test(int id);

    @Update("update test set name=#{name} where id=#{id}")
    int update_test(test t);

}
