package com.fsk.wechatroot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fsk.wechatroot.Util.HttpUtil;
import com.fsk.wechatroot.Util.JDThreadUtil;
import com.fsk.wechatroot.dao.Config;
import com.fsk.wechatroot.dao.InitCallback;
import com.fsk.wechatroot.dao.TextMessage;
import com.fsk.wechatroot.dao.User;
import com.fsk.wechatroot.mapper.ConfigMapper;
import com.fsk.wechatroot.mapper.MenuMapper;
import com.fsk.wechatroot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Author: fsk
 * Date: 2022/8/31 1:15
 * FileName: InitCallbackServiceImpl
 * Description: 微信检验 signature 实现类
 */
@Slf4j
@Service
public class InitCallbackServiceImpl implements InitCallbackService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private ConfigMapper configMapper;


    @Override
    public String CheckInit(InitCallback initCallback) {
        String checkSignature = "";
        List<String> list=new ArrayList<>();
        list.add(initCallback.getToken());
        list.add(initCallback.getTimestamp());
        list.add(initCallback.getNonce());
        Collections.sort(list);
        for (String s:list
             ) {
            checkSignature+=s;
        }
        checkSignature=DigestUtils.sha1Hex(checkSignature);

        if (checkSignature.equals(initCallback.getSignature())){
            return initCallback.getEchostr();
        }else{
            return "error";
        }



    }

    @Override
    public JSONObject GetAcc() {
        String result = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxeb0ae4d6c6a0b22c&secret=05512e90251ddff36de5459037f27919");

        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject= JSONObject.parseObject(result);
        return jsonObject;
    }

    @Override
    public String BackMessageText(String content,String username){
        List<Thread> threadSet = new ArrayList<>();
        List<FutureTask> futureTaskList = new ArrayList<>();
        log.info("进入BackMessageText");
        String backtext="";//返回文本
            switch (content){
                case "查询":
                  List<User> list=userMapper.selectForUsername(username);
                  if(list.size()==0){
                        backtext="未绑定账户，请发送ck绑定！";
                        return backtext;
                    }
                    for (User user:list
                         ) {
                        log.info("开始进入查询线程");
                        JDThreadUtil jdThreadUtil=new JDThreadUtil();
                        jdThreadUtil.setCookie(user.getCk());
                        FutureTask<String> result =new FutureTask<>(jdThreadUtil);
                        Thread thread=new Thread(result);
                        thread.start();
                        threadSet.add(thread);
                        futureTaskList.add(result);
                    }
                    for (Thread thread1 : threadSet) {
                        while (thread1.isAlive()){
                            try {
                                Thread.sleep(100);
                                log.info("查询主线程等待100");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    for (FutureTask f:futureTaskList
                         ) {
                        try {
                            backtext=backtext+f.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    log.info("查询结果返回"+backtext);
                    break;
                case "菜单":
                    List<String> stringList=menuMapper.SelectMenu(username);
                    if (stringList.size()==0){
                        backtext=backtext+"未提交ck,无法查看菜单\n";
                        break;
                    }
                    for (String s:stringList
                    ) {
                        backtext=backtext+s+"\n";
                    }
                    break;
                default:
                    if (content.indexOf("pt_key=")>0 && content.indexOf("pt_pin=")>0){
                        backtext=CheckAndPutCk(content,username);
                        break;
                    }else {
                        backtext="未找到该命令，请发送‘菜单’查看命令";
                        break;
                    }
            }
        return backtext;
    }

    @Override
    public String CheckAndPutCk(String putCk,String username) {
        String BackMsgtext="";
        HttpUtil httpUtil=new HttpUtil();
        String ck="";//添加到青龙里面的ck
        String nickname="";//别名

        //取出pt_key，pt_pin
        putCk=putCk.substring(putCk.indexOf("pt_key="),putCk.indexOf("pt_token="));
        //获取别名，检测ck是否有效
        String result=httpUtil.okhttp_get("https://me-api.jd.com/user_new/info/GetJDUserInfoUnion",putCk);
        JSONObject jsonObject=JSONObject.parseObject(result);
        nickname= (String) jsonObject.getJSONObject("data").getJSONObject("userInfo").getJSONObject("baseInfo").get("nickname");

        if (!((String) jsonObject.get("msg")).equals("success")){
            BackMsgtext="添加失败，请检查ck是否有效";
        }

        //检测是否已存在
        User user1=userMapper.selectByCk(putCk);
        if (user1!=null){
            BackMsgtext="已存在，请勿重复添加";
            return BackMsgtext;
        }

        User user2=userMapper.selectByTwoName(username,nickname);
        if (user2!=null){
            int code=userMapper.updateToCk(putCk,nickname,username);
            if (code>0){
                //格式化提交body
                ck="[{\"value\":\""+putCk+"\",\"name\":\"JD_COOKIE\",\"remarks\":\""+nickname+"\"}]";
                //添加到青龙面板里面
                httpUtil.okhttp_post("http://8.142.32.26:5701/open/envs?t=1661933847428",ck);
                BackMsgtext="更新成功!";
            }else {
                BackMsgtext="更新失败!请联系fsk";
            }
        }else {
            User user=new User();
            user.setUsername(username);
            user.setJdname(nickname);
            user.setCk(putCk);
            user.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            userMapper.insertUser(user);
            //格式化提交body
            ck="[{\"value\":\""+putCk+"\",\"name\":\"JD_COOKIE\",\"remarks\":\""+nickname+"\"}]";
            //添加到青龙面板里面
            httpUtil.okhttp_post("http://8.142.32.26:5701/open/envs?t=1661933847428",ck);
            BackMsgtext="添加成功，账户："+nickname;
        }

        return BackMsgtext;
    }

    /**
     * 按钮事件
     *
     * @param content  内容
     * @param username 用户名
     * @return {@code String}
     */
    @Override
    public String ButtonEvents(String content, String username) {

        String backtext="";//返回文本
        switch (content){
            case "查询":
                List<User> list=userMapper.selectForUsername(username);
                if(list.size()==0){
                    backtext="未绑定账户，请发送ck绑定！";
                    return backtext;
                }
                for (User user:list
                ) {
                    JDThreadUtil jdThreadUtil=new JDThreadUtil();
                    jdThreadUtil.setCookie(user.getCk());
                    FutureTask<String> result =new FutureTask<>(jdThreadUtil);
                    new Thread(result).start();
                    try {
                        System.out.println(result.get());
                        backtext=backtext+result.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "菜单":
                List<String> stringList=menuMapper.SelectMenu(username);
                if (stringList.size()==0){
                    backtext=backtext+"未提交ck,无法查看菜单\n";
                    break;
                }
                for (String s:stringList
                ) {
                    backtext=backtext+s+"\n";
                }
                break;
            case "新人指南":
                Config config= configMapper.getAll();
                String help=config.getHelp();
                backtext=backtext+help;
                break;
            case "提交ck":
                backtext=backtext+"请发送ck";
                break;
            default:
                Config config1= configMapper.getAll();
                String help1=config1.getHelp();
                backtext=backtext+help1;
                break;
        }
        return backtext;
    }



}
