package com.fsk.wechatroot.Util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Author: fsk
 * Date: 2022/9/8 21:38
 * FileName: ThreadUtil
 * Description: 线程-查询东东农场
 */
@Slf4j
@Data
public class FarmThreadUtil implements Callable<String> {


    String cookie;

    @Override
    public String call() throws Exception {
        log.info("进入FarmThreadUtil");
        String msg=getFarm(cookie);
        log.info(msg);
        return msg;
    }

    /**
     * 获取东东农场信息
     *
     * @param cookie cookie
     * @return {@code String}
     */
    private String getFarm(String cookie){
        String msg="";
        HttpUtil httpUtil=new HttpUtil();

        Map<String,String> map=new HashMap();
        map.put("body","{\"version\":4}");
        map.put("appid","wh5");
        map.put("clientVersion","9.1.0");

        String result=httpUtil.okhttp_post("https://api.m.jd.com/client.action?functionId=initForFarm",cookie,map);
        JSONObject jsonObject1=JSONObject.parseObject(result);
        int treeState= Integer.valueOf(String.valueOf(jsonObject1.get("treeState")));
        if(treeState<0){
            msg=msg+"【东东农场】：未种植水果，请手动选择种子！\n";
            return msg;
        }

        String name= (String) jsonObject1.getJSONObject("farmUserPro").get("name");//种植名称
        int treeEnergy= (int) jsonObject1.getJSONObject("farmUserPro").get("treeEnergy");//已浇水
        int treeTotalEnergy= (int) jsonObject1.getJSONObject("farmUserPro").get("treeTotalEnergy");//共需要浇水
        double plan=treeEnergy/ ((double) treeTotalEnergy);
        msg=msg+ "【东东农场】："+name+"("+String.format("%.2f", plan*100)+"%)\n";
        return msg;
    }


}
