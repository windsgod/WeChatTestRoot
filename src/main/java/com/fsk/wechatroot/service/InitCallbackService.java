package com.fsk.wechatroot.service;

import com.alibaba.fastjson.JSONObject;
import com.fsk.wechatroot.dao.InitCallback;
import com.fsk.wechatroot.dao.TextMessage;

import java.util.concurrent.Callable;

/**
 * Author: fsk
 * Date: 2022/8/31 1:13
 * FileName: InitCallbackService
 * Description: 微信检验 signature
 */
public interface InitCallbackService{

    public String CheckInit(InitCallback initCallback);

    public JSONObject GetAcc();

    public String BackMessageText(String content,String username);

    public String CheckAndPutCk(String putCk,String username);

    public String ButtonEvents(String content,String username);

}
