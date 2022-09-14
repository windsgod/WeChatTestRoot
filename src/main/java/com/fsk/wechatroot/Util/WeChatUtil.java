package com.fsk.wechatroot.Util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: fsk
 * Date: 2022/9/14 23:02
 * FileName: WeChatUtil
 * Description: 微信测试号工具类
 */
@Component
public class WeChatUtil {

    public List<String> getOpenid(String token){
        HttpUtil httpUtil=new HttpUtil();
        List<String> list=new ArrayList<>();
        String result=httpUtil.okhttp_simple_get("https://api.weixin.qq.com/cgi-bin/user/get?access_token="+token);
        JSONObject jsonObject=JSONObject.parseObject(result);
        list= ((List<String>) jsonObject.getJSONObject("data").get("openid"));
        return list;
    }

    public String getToken(){
        HttpUtil httpUtil=new HttpUtil();
        String result=httpUtil.okhttp_simple_get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx44932bb3b496e0c2&secret=c47ab9e4d06019c6587b174e49a3f5e5");
        JSONObject jsonObject=JSONObject.parseObject(result);
        String token= ((String) jsonObject.get("access_token"));
        return token;
    }


}
