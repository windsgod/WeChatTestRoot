package com.fsk.wechatroot.TaskJob;

import com.alibaba.fastjson.JSONObject;
import com.fsk.wechatroot.Util.HttpUtil;
import com.fsk.wechatroot.Util.JDThreadUtil;
import com.fsk.wechatroot.Util.WeChatUtil;
import com.fsk.wechatroot.dao.Config;
import com.fsk.wechatroot.dao.User;
import com.fsk.wechatroot.mapper.ConfigMapper;
import com.fsk.wechatroot.mapper.UserMapper;
import com.fsk.wechatroot.service.InitCallbackServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * Author: fsk
 * Date: 2022/9/8 18:32
 * FileName: timedPush
 * Description: 定时推送
 */
@Slf4j
@Component("timedPush")
public class TimedPush {

    @Autowired
    UserMapper userMapper;
    @Autowired
    InitCallbackServiceImpl initCallbackService;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    private WeChatUtil weChatUtil;



    //每日小结
    @Scheduled(cron ="0 0 13 * * ? ")//docker中cron不能正常运行 所以设置为13点 实际为21点
    private void push(){
        List<Thread> threadSet = new ArrayList<>();
        int SUCCESS=0,ERR=0;
        HttpUtil httpUtil=new HttpUtil();
        String token=weChatUtil.getToken();
        List<String> list=userMapper.getUsername();//获取数据库中存入ck的用户列表
        for (String s:list
             ) {
            String context="";
            List<User> list1 =userMapper.selectForUsername(s);
            for (User user:list1
                 ) {
                JDThreadUtil jdThreadUtil=new JDThreadUtil();
                jdThreadUtil.setCookie(user.getCk());
                FutureTask<String> result =new FutureTask<String>(jdThreadUtil);
                Thread thread=new Thread(result);
                thread.start();
                threadSet.add(thread);
                try {
                    context=context+result.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (Thread t:threadSet
                 ) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String body="{\n" +
                    "    \"touser\": \""+s+"\",\n" +
                    "    \"template_id\": \"ahDueJldSnvSnUWGN44RgwbZAcZrdIIws4vAD85aWeI\",\n" +
                    "    \"url\": \"\",\n" +
                    "    \"topcolor\": \"#FF0000\",\n" +
                    "    \"data\": {\n" +
                    "        \"title\": {\n" +
                    "            \"value\": \"今日收益小结\",\n" +
                    "            \"color\": \"#173177\"\n" +
                    "        },\n" +
                    "        \"context\": {\n" +
                    "            \"value\": \""+context+"\",\n" +
                    "            \"color\": \"#173177\"\n" +
                    "        },\n" +
                    "         \"remark\": {\n" +
                    "            \"value\": \"今日结束，祝君早日发财    -fsk\",\n" +
                    "            \"color\": \"#173177\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            JSONObject jsonObject=JSONObject.parseObject(body);
            String result=httpUtil.okhttp_post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token,jsonObject);
            log.info("推送返回结果"+result);
            JSONObject jsonObject1=JSONObject.parseObject(result);
            if (String.valueOf(jsonObject1.get("errmsg")).equals("ok")){
                SUCCESS=SUCCESS+1;
            }else {
                ERR=ERR+1;
            }
            /*间隔五秒*/
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String body="{\n" +
                "    \"touser\": \"oQnEX6nGmt2ZAmX0-J8Iykr_mv3E\",\n" +
                "    \"template_id\": \"l8P5xjmqBvplxAzulM6MAQfJ4NaQAG_MdsrehbiBtvw\",\n" +
                "    \"url\": \"\",\n" +
                "    \"topcolor\": \"#FF0000\",\n" +
                "    \"data\": {\n" +
                "        \"SUCCESS\": {\n" +
                "            \"value\": \""+SUCCESS+"\",\n" +
                "            \"color\": \"#173177\"\n" +
                "        },\n" +
                "        \"ERR\": {\n" +
                "            \"value\": \""+ERR+"\",\n" +
                "            \"color\": \"#173177\"\n" +
                "        } "+
                "    }\n" +
                "}";
        JSONObject jsonObject=JSONObject.parseObject(body);
        String result=httpUtil.okhttp_post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token,jsonObject);
        log.info(result);
    }


    /**
     * 定时检查ck是否有效
     */
    @Scheduled(cron ="0 0 13 * * ? ")
    private void Check() {
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
                String body = "{\n" +
                        "    \"touser\": \"" + s + "\",\n" +
                        "    \"template_id\": \"UZNcQ_tCJwH4E4vAmd1OFin5GOSPkHS63SjOassbzEA\",\n" +
                        "    \"url\": \"\",\n" +
                        "    \"topcolor\": \"#FF0000\",\n" +
                        "    \"data\": {\n" +
                        "        \"jdname\": {\n" +
                        "            \"value\": \""+s1+"\",\n" +
                        "            \"color\": \"#173177\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject = JSONObject.parseObject(body);
                String result = httpUtil.okhttp_post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token, jsonObject);
                log.info("失效账户推送用户返回结果" + result);
                String body1 = "{\n" +
                        "    \"touser\": \"oQnEX6nGmt2ZAmX0-J8Iykr_mv3E\",\n" +
                        "    \"template_id\": \"UZNcQ_tCJwH4E4vAmd1OFin5GOSPkHS63SjOassbzEA\",\n" +
                        "    \"url\": \"\",\n" +
                        "    \"topcolor\": \"#FF0000\",\n" +
                        "    \"data\": {\n" +
                        "        \"jdname\": {\n" +
                        "            \"value\": \""+s1+"\",\n" +
                        "            \"color\": \"#173177\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                JSONObject jsonObject1 = JSONObject.parseObject(body);
                String result1 = httpUtil.okhttp_post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token, jsonObject1);
                log.info("失效账户推送管理员返回结果" + result1);
            }
        }
    }
}
