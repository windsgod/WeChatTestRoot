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
 * Description: 线程-查询账户信息,京豆，E卡，红包
 */
@Slf4j
@Data
public class JDUserThreadUtil implements Callable<String> {

    private String cookie;

    @Override
    public String call() throws Exception {
        log.info("进入JDUserThreadUtil");
        String msg=getJDUser(cookie);
        log.info(msg);
        return msg;
    }

    private String getJDUser(String ck) {
        String backtext="";
        String cookie="shshshfpa=1ba6d194-751d-8b02-8043-ed117816e4c4-1645159777; shshshfpb=tetzQwwmW19gzx_jbHosUPA; mobilev=html5; __jdv=122270672%7Cdirect%7C-%7Cnone%7C-%7C1662000624168; __jdc=122270672; mba_muid=1662000624168306525223; wxa_level=1; cid=9; jxsid=16624450856661317320; appCode=ms0ca95114; webp=1; __jda=122270672.1662000624168306525223.1662000624.1662000624.1662445085.2; visitkey=35444731050923766; retina=1; PPRD_P=UUID.1662000624168306525223; sc_width=557; equipmentId=2FQ35HG4OVOTK6OZ2V5Z6C6J44WLURKY3RYZL526F2EC6MWCXCISPFZIBVSLGLE3WZQYW3O7MDPMCFXHPRQ7EJBSSU; deviceVersion=104.0.5112.102; deviceOS=android; deviceOSVersion=6.0; deviceName=Chrome; _gia_s_e_joint={\"eid\":\"2FQ35HG4OVOTK6OZ2V5Z6C6J44WLURKY3RYZL526F2EC6MWCXCISPFZIBVSLGLE3WZQYW3O7MDPMCFXHPRQ7EJBSSU\",\"ma\":\"\",\"im\":\"\",\"os\":\"Android 6.x\",\"ip\":\"101.86.126.125\",\"ia\":\"\",\"uu\":\"\",\"at\":\"6\"}; jcap_dvzw_fp=meTzzzqiJSr249o_oD1kxjDbr9b0i9wPMX6SfPzDDi9mCfI0Gx3Z_ukJLG92dh94ZH9TZA==; _gia_s_local_fingerprint=8e2557c2eea9e455816cdc8078579270; fingerprint=8e2557c2eea9e455816cdc8078579270; whwswswws=; 3AB9D23F7A4B3C9B=2FQ35HG4OVOTK6OZ2V5Z6C6J44WLURKY3RYZL526F2EC6MWCXCISPFZIBVSLGLE3WZQYW3O7MDPMCFXHPRQ7EJBSSU; TrackerID=hITFA9DOvg2j6WoC7rwp0zE_QjZMofWlxttuG-RCWqUw280Kn-jtKTIHOtnJbTbOVSR9Kp17KMshOOLrxoPyk9pfxhS0NNrHXc57h56sxvw-jkHdLqL22SGn6HGHJ5FwQ54pxmXOY7zfWUcJFhk-yw; "
                +ck+"pt_token=skpaegai; pwdt_id=jd_64de2e62cc97a; sfstoken=tk01ma4a81bdda8sMisxKzMrMjhlY8lg+2HG30+KwQj1Ucn+/+uendFJWo//iDl8dRNh6jHrhZCMFJ55klKDzJryKy6j; shshshfp=fdbaa7ec6757fb1691de30e6fffac471; autoOpenApp_downCloseDate_jd_homePage=1662445371406_1; cartNum=12; kplTitleShow=1; wq_addr=4551928385%7C1_72_2819_0%7C%u5317%u4EAC_%u671D%u9633%u533A_%u4E09%u73AF%u5230%u56DB%u73AF%u4E4B%u95F4_%7C%u5317%u4EAC%u671D%u9633%u533A%u4E09%u73AF%u5230%u56DB%u73AF%u4E4B%u95F4%7C116.444%2C39.9219; jdAddrId=1_72_2819_0; jdAddrName=%u5317%u4EAC_%u671D%u9633%u533A_%u4E09%u73AF%u5230%u56DB%u73AF%u4E4B%u95F4_; commonAddress=4551928385; regionAddress=1%2C72%2C2819%2C0; mitemAddrId=1_72_2819_0; mitemAddrName=%u5317%u4EAC%u671D%u9633%u533A%u4E09%u73AF%u5230%u56DB%u73AF%u4E4B%u95F4; wqmnx1=MDEyNjM5M3BqL21hZDA3PTgzNi91aWVpTmUuTGVvLiBhLjAucmE0MU8jKQ%3D%3D; __jdb=122270672.30.1662000624168306525223|2.1662445085; mba_sid=16624450857368230145383992680.30; __wga=1662445623489.1662445095587.1662445095587.1662445095587.10.1; jxsid_s_t=1662445623521; jxsid_s_u=https%3A//wqs.jd.com/my/asset.html; shshshsID=00a143ce613f182f4376b0f64271d14b_16_1662445623711";

        HttpUtil httpUtil=new HttpUtil();
        String result="";

        /*
         * 账户信息,京豆，E卡，红包
         *
         * */
        result=httpUtil.okhttp_get("https://me-api.jd.com/user_new/info/GetJDUserInfoUnion",ck);
        JSONObject jsonObject=JSONObject.parseObject(result);
        if (jsonObject.get("msg").equals("success")){
            String nickname= (String) jsonObject.getJSONObject("data").getJSONObject("userInfo").getJSONObject("baseInfo").get("nickname");//昵称
            String levelName=(String) jsonObject.getJSONObject("data").getJSONObject("userInfo").getJSONObject("baseInfo").get("levelName");//等级名称
            String userLevel=(String) jsonObject.getJSONObject("data").getJSONObject("userInfo").getJSONObject("baseInfo").get("userLevel");//等级
            int beanNum= Integer.valueOf((String) jsonObject.getJSONObject("data").getJSONObject("assetInfo").get("beanNum")).intValue();//京豆明细
            String redBalance=(String) jsonObject.getJSONObject("data").getJSONObject("assetInfo").get("redBalance");//红包
            backtext=backtext+
                    "【账户昵称】："+nickname+"\n"+
                    "【账户信息】："+levelName+"("+userLevel+")\n"+
                    "【当前京豆】："+beanNum+"(≈"+ ((double) beanNum)/100+"元)\n"+
                    "【红包总额】："+redBalance+"\n";
        }else if (jsonObject.get("msg").equals("not login")){
            backtext=backtext+"ck已经失效！请重新提交ck";
            return backtext;

        }
        return backtext;
    }


}
