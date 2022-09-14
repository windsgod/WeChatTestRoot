package com.fsk.wechatroot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fsk.wechatroot.TaskJob.TimedPush;
import com.fsk.wechatroot.Util.HttpUtil;
import com.fsk.wechatroot.dao.Config;
import com.fsk.wechatroot.dao.User;
import com.fsk.wechatroot.mapper.ConfigMapper;
import com.fsk.wechatroot.mapper.MenuMapper;
import com.fsk.wechatroot.mapper.UserMapper;

import com.fsk.wechatroot.service.InitCallbackServiceImpl;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


@SpringBootTest
class WeChatRootApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    ConfigMapper configMapper;

    @Autowired
    InitCallbackServiceImpl initCallbackService;




    @Test
    void contextLoads(){

//        HttpUtil httpUtil=new HttpUtil();
//        System.out.println(httpUtil.okhttp_get("http://8.142.32.26:5701/api/crons/zE9iopjJTbxAHpf9/log?t=1661933847428"));

//        String backtext="【账号1\uD83C\uDD94】jd_64de2e62cc97a\\n【账号信息】\uD83D\uDC8E钻石Plus,京享值7658\\n【今日京豆】收62豆\\n【昨日京豆】收29豆,支952豆\\n【当前京豆】62豆(≈0.62元)\\n【今日喜豆】收0豆\\n【昨日喜豆】收0豆\\n【当前喜豆】0喜豆(≈0.00元)\\n【京喜牧场】3276枚鸡蛋\\n【极速金币】427018币(≈42.70元)\\n【京东赚赚】190550币(≈19.05元)\\n【京东秒杀】4046币(≈4.05元)\\n【礼卡余额】共1张E卡,合计100.00元\\n【其他信息】领现金:9.57元\\n【东东农场】可爱创意手机支架(12%,10天)\\n【京喜工厂】床上三件套(100%\\n\uD83E\uDDE7\uD83E\uDDE7\uD83E\uDDE7红包明细\uD83E\uDDE7\uD83E\uDDE7\uD83E\uDDE7\\n【红包总额】1.42(总过期0.42)元 \\n【京喜红包】1.34(将过期0.37)元 \\n【极速红包】0.08(将过期0.05)元 \\n【免运费券】3张(有效期至2022-09-18)\\n\\n";
//        backtext=backtext.replace("\\n","\n");
//        System.out.println(backtext);

       /* InitCallbackServiceImpl initCallbackService=new InitCallbackServiceImpl();
        System.out.println(initCallbackService.BackMessageText("查询:jd_610a2721f1fb5"));*/

       /* HttpUtil httpUtil=new HttpUtil();
        System.out.println(httpUtil.okhttp_post("http://8.142.32.26:5701/api/envs?t=1661933847428","[{\"value\":\"fdsfsdf\",\"name\":\"JD_COOKIE\",\"remarks\":\"adsdsdsdsadsasdssdasadds\"}]"));
*/
       /* InitCallbackServiceImpl initCallbackService=new InitCallbackServiceImpl();
        String ck="wxa_level = 1; retina = 1; cid = 9; appCode = ms0ca95114; webp = 1; __jda = 122270672.16602732502051455376502.1660273250.1660273250.1660273250.1; __jdv = 122270672 % 7Cdirect % 7C -% 7Cnone % 7C -% 7C1660273250206; __jdc = 122270672; mba_muid = 16602732502051455376502; visitkey = 36719872203370862; jxsid = 16602732695545205119; PPRD_P = UUID.16602732502051455376502; jxsid_s_u = https % 3A//home.m.jd.com/myJd/newhome.action; sc_width=384; shshshfpb=vE-FYGMNYIheCtDsUFlQiwQ; shshshfpa=1c396562-4451-5f81-2b68-47fee27568a9-1645002225; equipmentId=FCJDDZJPEMTUFIOOKYD456BSZA3FRMVKUUTMM36JOYVCKW6RF3NMTEAZYFKHSWLNOJNT4DJURPWUI443AKHYFVP6F4; fingerprint=c3a08c7675287d3577ca0a2cafb92d9b; deviceVersion=96.0.4664.104; deviceOS=android; deviceOSVersion=10; deviceName=Chrome; jcap_dvzw_fp=tlKKTC_LgKbPGDiWIuE89NGmCLljlLrWWYipVhaN_AkLR8hhoAo-n_Wm2znXfcX1IlcULg==; TrackerID=etxgNKEyJkUiDTzPUMyfPsT5E7m7jWn_HMgNjou6KXEbnfEbs4sHnp3KND-foY_KV_nkMonEJzHmmtzOoxCVz_HiBi-U_dd0Y1BZ_2ak1BHma2avgWL_X5iwoPif-YsG; pt_key=AAJi9cLcADBLVjgSNu_XUW0fbACbuBAbrJ4ppw52q_0eUUQ6Jrl8jIlKxGfm_BIwLBwRJP_ptl4; pt_pin=jd_oDBfuXylFOXI; pt_token=ux12hhy4; pwdt_id=jd_oDBfuXylFOXI; sfstoken=tk01mfb8f1d8aa8sMngyeDMrMSsyOKmcv3HTvqwjafTPf6wSXYlpUfdBWhg1ZnBTJgQ7cP9zyFTU7/LRgNm735zuRmmo; whwswswws=; shshshfp=287165db451e0fb3ee2ca5f84624a0d8; downloadAppPlugIn_downCloseDate=1660273380959_1800000; __wga=1660273384190.1660273270194.1660273270194.1660273270194.3.1; jxsid_s_t=1660273384270; shshshsID=b1967df40c6aec028b14979c4e9c7be4_4_1660273384478; wqmnx1=MDEyNjM3NHNkc2xpMjUxMTNNL2luMS1BYjdIaWtvMDFpYS4zMlVCMlJJKikl; __jdb=122270672.10.16602732502051455376502|1.1660273250; mba_sid=16602732502083047380759299001.10; autoOpenApp_downCloseDate_jd_homePage=1660273386908_1; __jd_ref_cls=MGuessYouLike_ProExpo";
        System.out.println(initCallbackService.CheckAndPutCk(ck));*/

//          HttpUtil httpUtil=new HttpUtil();
//          System.out.println(httpUtil.okhttp_get("https://me-api.jd.com/user_new/info/GetJDUserInfoUnion","pt_key=AAJi-Pi3ADAYq5WPSTmYU8EAj2k83qc4HRQtag5HUWfYHhDx8mxZ7nJ8mxPTnFvdwZGLObPF45U;pt_pin=jd_7e4fc37a2d1d2;"));

/*
            String s="{\"data\":{\"JdVvipCocoonInfo\":{\"JdVvipCocoonStatus\":\"1\"},\"JdVvipInfo\":{\"jdVvipStatus\":\"-1\"},\"assetInfo\":{\"accountBalance\":\"0.00\",\"baitiaoInfo\":{\"availableLimit\":\"500.00\",\"baiTiaoStatus\":\"0\",\"bill\":\"1\",\"billOverStatus\":\"0\",\"outstanding7Amount\":\"0.00\",\"overDueAmount\":\"0.00\",\"overDueCount\":\"0\",\"unpaidForAll\":\"0.00\",\"unpaidForMonth\":\"0.00\"},\"beanNum\":\"507\",\"btFfkInfo\":{\"appId\":\"wxe417c6951d540ef0\",\"linkUrl\":\"\",\"numText\":\"\",\"numUnitText\":\"\",\"status\":\"1\",\"subtitle\":\"\",\"title\":\"白条分分卡\"},\"couponNum\":\"95\",\"couponRed\":\"\",\"redBalance\":\"1.00\"},\"favInfo\":{\"contentNum\":\"\",\"favDpNum\":\"\",\"favGoodsNum\":\"11\",\"favShopNum\":\"7\",\"footNum\":\"19\",\"isGoodsRed\":\"\",\"isShopRed\":\"\"},\"gameBubbleList\":[{\"carouselInfos\":[{\"icon\":\"\",\"text\":\"2京豆\"}],\"key\":\"bean\",\"title\":\"领京豆\"}],\"growHelperCoupon\":{\"addDays\":0,\"batchId\":0,\"couponKind\":0,\"couponModel\":0,\"couponStyle\":0,\"couponType\":0,\"discount\":0.0,\"limitType\":0,\"msgType\":0,\"quota\":0.0,\"roleId\":0,\"state\":0,\"status\":-1},\"kplInfo\":{\"kplInfoStatus\":\"-1\",\"mopenbp17\":\"\",\"mopenbp22\":\"\"},\"orderInfo\":{\"commentCount\":\"3\",\"logistics\":[],\"orderCountStatus\":\"0\",\"receiveCount\":\"1\",\"waitPayCount\":\"0\"},\"plusFloor\":{\"leftTabs\":[{\"contentType\":0,\"imageUrl\":\"https://m.360buyimg.com/babel/jfs/t1/2028/12/16639/2882/626ab86cE3339ff93/c3a4091e70535f22.png\",\"link\":\"https://plus.m.jd.com/index?s=xcx\",\"subTitle\":\"10倍返京豆\",\"title\":\"PLUS中心\"}],\"midTabs\":[{\"contentType\":0,\"imageUrl\":\"https://m.360buyimg.com/babel/jfs/t1/202813/1/13327/11088/617ab759Ef06ff292/749e9ea1f0ff2a79.png\",\"link\":\"https://plus.m.jd.com/coupon/exclusive\",\"subTitle\":\"天天抢\",\"title\":\"专享好券\"}],\"rightTabs\":[{\"contentType\":0,\"imageUrl\":\"https://m.360buyimg.com/babel/jfs/t1/222476/33/15191/14437/624fc9bcE9ba943b1/d430444dd7885475.png\",\"link\":\"https://prodev.m.jd.com/mini/active/3DhhYrumA8xM4CkhmnVuRLxeGVTw/index.html\",\"subTitle\":\"服饰9折\",\"title\":\"专享福利\"}]},\"plusPromotion\":{\"status\":-1},\"tfAdvertInfo\":{\"status\":\"-1\"},\"userInfo\":{\"baseInfo\":{\"accountType\":\"0\",\"baseInfoStatus\":\"0\",\"curPin\":\"jd_7e4fc37a2d1d2\",\"definePin\":\"0\",\"headImageUrl\":\"\",\"levelName\":\"铜牌用户\",\"nickname\":\"jd_iREUcWnNSLww\",\"userLevel\":\"56\"},\"isHideNavi\":\"0\",\"isHomeWhite\":\"1\",\"isJTH\":\"0\",\"isKaiPu\":\"0\",\"isPlusVip\":\"0\",\"isQQFans\":\"0\",\"isRealNameAuth\":\"1\",\"isWxFans\":\"0\",\"jvalue\":\"\",\"orderFlag\":\"1\",\"plusInfo\":{},\"tmpActWaitReceiveCount\":\"0\",\"xbKeepLink\":\"https://agree.jd.com/m/index.html\",\"xbKeepOpenStatus\":\"1\",\"xbKeepScore\":\"679\",\"xbScore\":\"74.9\"},\"userLifeCycle\":{\"identityId\":\"10\",\"lifeCycleStatus\":\"0\",\"trackId\":\"100037:0:10:0\"}},\"msg\":\"success\",\"retcode\":\"0\",\"timestamp\":1662004803104}\n";
            s=s.substring(s.indexOf("nickname")+11,s.indexOf("userLevel")-3);
            System.out.println(s);
*/
/*            test t=new test();
            t.setId(4);
            t.setName("c");
//            testMapper.insert_test(t);
//            testMapper.delete_test(3);
        testMapper.update_test(t);
            List<test> tests=testMapper.getAll();
        for (test s:tests
             ) {
            System.out.println(s.toString());
        }*/


      /*  User user=userMapper.getJdname("oQnEX6nGmt2ZAmX0-J8Iykr_mv3E");
        System.out.println(user.toString());*/
       /* User user=new User();
        user.setUsername("fsk");
        user.setJdname("skfan");
        user.setCk("ck");
        SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        user.setTime(ft.format(new Date()));
        userMapper.insertUser(user);*/

      /*List<String> stringList=menuMapper.SelectMenu("oQnEX6nGmt2ZAmX0-J8Iykr_mv3E");
        String content="";
        for (String s:stringList
             ) {
            content=content+s+"\n";
        }
        System.out.println(content);*/



        /*  HttpUtil httpUtil=new HttpUtil();
         *//* String s=httpUtil.okhttp_get("http://8.142.32.26:5701/api/envs?searchValue=&t=1662187628494");
        JSONObject jsonObject=JSONObject.parseObject(s);
        JSONArray jsonArray= jsonObject.getJSONArray("data");
        s= (String) jsonArray.getJSONObject(0).get("value");
        System.out.println(s);*//*
        String s=httpUtil.getAuthorization();
        System.out.println(s);*/

/*        User user=userMapper.getJdname("oQnEX6nGmt2ZAmX0-J8Iykr_mv3E");
        if (user==null){
            System.out.println(123);
        }else {
            System.out.println(321);
        }*/

      /*  List<User> list= userMapper.selectForUsername("oQnEX6rZCtQRih4KjtF04ZhqxFS");
        for (User u:list
             ) {
            System.out.println(u.toString());
        }*/

/*
        TimedPush timedPush=new TimedPush();
        String token=timedPush.getToken();
        List<String> list=timedPush.getOpenid(token);
        for (String s:list
             ) {
            System.out.println(s);
        }
*/

/*

        HttpUtil httpUtil=new HttpUtil();
        String s="oQnEX6nGmt2ZAmX0-J8Iykr_mv3E";
        String context="";
        List<User> list1 =userMapper.selectForUsername(s);
        for (User user:list1
        ) {
            context=context+initCallbackService.Getjd(user.getCk());
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
        System.out.println(jsonObject.getJSONObject("data").get("context"));
        String result=httpUtil.okhttp_post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=60_YbqjOz9NggXXfYkYIGPIuFYbrnL1fzIuhR4GT9tkRiv6N0BHOqMdaCo8TOn-vlpqqMfN6QYFw-W_l98p3yxDWmYk_rnxvWcxVRU2SGZhTni3_mzR4l5qiMJEgkMPDBnsIh3Bc12i7WlWgyAJJMPfADATWZ",jsonObject);
        System.out.println(result);
    }
*/

       /* Config config=configMapper.getAll();
        System.out.println(config);*/



/*
        for (int i = 0; i < 10; i++) {
            log.info("info");
            log.debug("debug");
            log.error("error");
        }
*/
/*

        FutureTask<String> futureTask=new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String s="线程1";
                int i=1000;
                while (i<0){
                    i--;
                }
                return s;
            }
        });

        FutureTask<String> futureTask1=new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String s="线程2";
                int i=10;
                while (i<0){
                    i--;
                }
                return s;
            }
        });


        Thread thread=new Thread(futureTask);
        Thread thread1=new Thread(futureTask1);

        try {
            thread1.join();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(futureTask.get()+futureTask1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
*/



     /*   System.out.println("1");*/



    }
}
