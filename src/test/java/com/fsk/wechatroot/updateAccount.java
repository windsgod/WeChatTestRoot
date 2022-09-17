package com.fsk.wechatroot;

import com.alibaba.fastjson.JSONObject;
import com.fsk.wechatroot.Util.HttpUtil;
import com.fsk.wechatroot.Util.WeChatUtil;
import com.fsk.wechatroot.dao.User;
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
 * Date: 2022/9/17 22:35
 * FileName: updateAccount
 * Description: 自动添加账户
 */
@SpringBootTest
public class updateAccount {

    @Autowired
    private InitCallbackServiceImpl initCallbackService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatUtil weChatUtil;


    @Test
    void submitAllAccount(){
      /*  List<User> userList=userMapper.getAll();
        for (User u:userList
             ) {
            System.out.println(u.getJdname()+":开始添加");
            String msg=initCallbackService.CheckAndPutCk(u.getCk(),u.getUsername());
            System.out.println(msg);
            System.out.println(u.getJdname()+":添加完成");
        }*/
        System.out.println("123");
    }


    @Test
    void testFailure(){
            Map<String, List<String>> map = new HashMap<>();
            int SUCCESS = 0, ERR = 0;
            HttpUtil httpUtil = new HttpUtil();
            String token = weChatUtil.getToken();
            List<String> list = userMapper.getUsername();
            for (String s : list
            ) {
                String context = "";
                List<User> list1 = userMapper.selectForUsername(s);
                List<String> list2=new ArrayList<>();
                for (User user : list1
                ) {
                    String result=httpUtil.okhttp_get("https://me-api.jd.com/user_new/info/GetJDUserInfoUnion",user.getCk());
                    JSONObject jsonObject=JSONObject.parseObject(result);
                    if (!((String) jsonObject.get("msg")).equals("success")){
                        list2.add(user.getJdname());//如果失效，存入数组list2
                    }
                }
                map.put(s,list2);//失效的ck数组和对应的微信id
            }
        for (String s:map.keySet()
        ) {
            List<String> list1=map.get(s);
            for (String s1:list1
            ) {
                System.out.println(s+":"+s1);
            }
        }
    }

}
