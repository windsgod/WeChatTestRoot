package com.fsk.wechatroot.Util;

import com.fsk.wechatroot.dao.User;
import com.fsk.wechatroot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Author: fsk
 * Date: 2022/9/17 23:19
 * FileName: submitAllAccountUtil
 * Description: 添加数据库中的账户信息到青龙面板
 */
public class submitAllAccountUtil {

    @Autowired
    private UserMapper userMapper;

    void submitAllAccount(){
        HttpUtil httpUtil=new HttpUtil();
        List<User> userList=userMapper.getAll();
        for (User u:userList
        ) {
            System.out.println(u.getJdname()+":开始添加");
            //格式化提交body
            String ck="[{\"value\":\""+u.getCk()+"\",\"name\":\"JD_COOKIE\",\"remarks\":\""+u.getJdname()+"\"}]";
            //添加到青龙面板里面
            String result=httpUtil.okhttp_post("http://8.142.32.26:5701/open/envs?t=1661933847428",ck);
            System.out.println(result);
            System.out.println(u.getJdname()+":添加完成");
        }
    }


}
