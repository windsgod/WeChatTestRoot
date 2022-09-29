package com.fsk.wechatroot.Util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private HttpUtil httpUtil;


    String cookie;

    @Override
    public String call() throws Exception {
        log.info("进入FarmThreadUtil");
        String msg = getFarm(cookie);
        log.info(msg);
        return msg;
    }

    /**
     * 获取东东农场信息
     *
     * @param cookie cookie
     * @return {@code String}
     */
    private String getFarm(String cookie) {
        String msg = "";

        Map<String, String> map = new HashMap();
        map.put("body", "{\"version\":4}");
        map.put("appid", "wh5");
        map.put("clientVersion", "9.1.0");

        String result = httpUtil.okhttp_post("https://api.m.jd.com/client.action?functionId=initForFarm", cookie, map);
        JSONObject jsonObject1 = JSONObject.parseObject(result);

        int treeState = jsonObject1.getIntValue("treeState");
        System.out.println(treeState);
        if (treeState < 0) {
            msg = msg + "【东东农场】：未种植水果，请手动选择种子！\n";
            return msg;
        }
        if (jsonObject1.getJSONObject("farmUserPro")!=null){
            String name = jsonObject1.getJSONObject("farmUserPro").getString("name");//种植名称
            int treeEnergy = jsonObject1.getJSONObject("farmUserPro").getIntValue("treeEnergy");//已浇水
            int treeTotalEnergy = jsonObject1.getJSONObject("farmUserPro").getIntValue("treeTotalEnergy");//共需要浇水
            double plan = treeEnergy / ((double) treeTotalEnergy);
            msg = msg + "【东东农场】：" + name + "(" + String.format("%.2f", plan * 100) + "%)\n";
        }else{
            msg = msg + "【东东农场】：接口返回结果异常\n";
        }
        return msg;
    }


}
