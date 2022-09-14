package com.fsk.wechatroot.Util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Author: fsk
 * Date: 2022/9/8 21:38
 * FileName: ThreadUtil
 * Description: 线程-查询京东资产
 */
@Slf4j
@Data
public class JDThreadUtil implements Callable<String> {

    String cookie;

    @Override
    public String call() throws Exception {
        log.info("进入JDThreadUtil");
        String backtext="";
        jdUserThreadUtil.setCookie(cookie);
        petThreadUtil.setCookie(cookie);
        farmThreadUtil.setCookie(cookie);
        FutureTask<String> result =new FutureTask<String>(jdUserThreadUtil);
        FutureTask<String> result1 =new FutureTask<String>(farmThreadUtil);
        FutureTask<String> result2 =new FutureTask<String>(petThreadUtil);

        Thread thread=new Thread(result);
        Thread thread1=new Thread(result1);
        Thread thread2=new Thread(result2);
        thread.start();
        thread1.start();
        thread2.start();

        //等待三个子线程执行结束
       /* thread.join();
        log.info("thread.join()执行完毕");
        thread1.join();
        log.info("thread1.join()执行完毕");
        thread2.join();
        log.info("thread2.join()执行完毕");*/
        while (thread2.isAlive()){
            Thread.sleep(100);
            log.info("任务主线程等待100");
        }


        String msg1=result.get();
        String msg2=result1.get();
        String msg3=result2.get();

        backtext=backtext+msg1+msg2+msg3;

        log.info("JDThreadUtil结束");

        return backtext;
    }

    JDUserThreadUtil jdUserThreadUtil=new JDUserThreadUtil();
    PetThreadUtil petThreadUtil=new PetThreadUtil();
    FarmThreadUtil farmThreadUtil=new FarmThreadUtil();

}
