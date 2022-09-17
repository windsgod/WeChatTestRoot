package com.fsk.wechatroot.Util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Author: fsk
 * Date: 2022/9/8 21:38
 * FileName: ThreadUtil
 * Description: 线程-查询东东萌宠
 */
@Slf4j
@Data
public class PetThreadUtil implements Callable<String> {

    String cookie;

    @Override
    public String call() throws Exception {
        log.info("进入PetThreadUtil");
        String msg=petInformation(cookie);
        log.info(msg);
        return msg;
    }

    /**
     * 获取东东宠物信息
     *
     * @param cookie cookie
     * @return {@code String}
     */
    private String petInformation(String cookie){

        String msg="";
        HttpUtil httpUtil=new HttpUtil();

        Map<String,String> map1=new HashMap();
        map1.put("body","%7B%22version%22%3A2%2C%22channel%22%3A%22app%22%7D");
        map1.put("appid","wh5");
        map1.put("loginWQBiz","pet-town");
        map1.put("clientVersion","9.0.4");


        Map<String,String> map2=new HashMap();
        map2.put("body","%7B%22version%22%3A2%2C%22channel%22%3A%22app%22%7D");
        map2.put("appid","wh5");
        map2.put("loginWQBiz","pet-town");
        map2.put("clientVersion","9.0.4");


        String result2=httpUtil.okhttp_post("https://api.m.jd.com/client.action?functionId=initPetTown",cookie,map1);
        log.info("萌宠接口2"+result2);

        JSONObject jsonObject2 = JSONObject.parseObject(result2);

        if ( result2.equals("请求失败")){
            msg=msg+"***********************************\n";
            return msg;
        }
        if ( ((int) jsonObject2.getJSONObject("result").get("resultCode"))==410){
            msg=msg+"活动太火爆了，稍后试试吧\n***********************************\n";
            return msg;
        }

        if (((int) jsonObject2.getJSONObject("result").get("userStatus"))==0){
            msg=msg+"【东东萌宠】活动未开启!\n";
        }
        if (((int) jsonObject2.getJSONObject("result").get("petStatus"))==5){
            msg=msg+"【东东萌宠】"+ ((String) jsonObject2.getJSONObject("result").getJSONObject("goodsInfo").get("goodsName"))+"已可领取!\\n";
        }
        if(((int) jsonObject2.getJSONObject("result").get("petStatus"))==6){
            msg=msg+"【东东萌宠】未选择物品! \n";
        }

        msg=msg+"【东东萌宠】:"+((String) jsonObject2.getJSONObject("result").getJSONObject("goodsInfo").get("goodsName"))+","
                    +((int) jsonObject2.getJSONObject("result").get("medalNum"))+"/"+
                    ((int) jsonObject2.getJSONObject("result").getJSONObject("goodsInfo").get("exchangeMedalNum"))+"块("+
                    (Double.valueOf(String.valueOf(jsonObject2.getJSONObject("result").get("medalPercent"))))+"%)\n";

        if (String.valueOf(jsonObject2.getJSONObject("result").get("goodsInfo")).equals("")){
            msg=msg+"【东东萌宠】暂未选购新的商品! \n";
        }
        log.info(msg);
        msg=msg+"***********************************\n";
        return msg;
    }


}
