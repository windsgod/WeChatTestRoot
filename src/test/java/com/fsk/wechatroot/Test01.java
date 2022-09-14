package com.fsk.wechatroot;

import com.fsk.wechatroot.mapper.ConfigMapper;
import com.fsk.wechatroot.mapper.MenuMapper;
import com.fsk.wechatroot.mapper.UserMapper;
import com.fsk.wechatroot.service.InitCallbackServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: fsk
 * Date: 2022/9/14 23:37
 * FileName: Test01
 * Description: 单元测试01
 */


@SpringBootTest
public class Test01 {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    ConfigMapper configMapper;

    @Autowired
    InitCallbackServiceImpl initCallbackService;

    @Test
    void TestUserMapper() {

        /*List<String> list=userMapper.getUsername();
        for (String s:list
        ) {
            System.out.println(s);
        }*/

       /* String username="oQnEX6vkRmzTagY4Rydlgn15BggA";
        List<String> stringList=menuMapper.SelectMenu(username);
        System.out.println(stringList.size());
*/

     /*   Map<String,List<String>> map=new HashMap<>();
        String z="a";
        String k="0";
        for (int i = 0; i < 5; i++) {
            z=z+i;
            List<String> list=new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                k=k+j;
                list.add(k);
            }
            map.put(z,list);
        }
        System.out.println("1");
    }*/

    }

}
