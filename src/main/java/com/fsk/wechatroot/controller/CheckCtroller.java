package com.fsk.wechatroot.controller;

import com.alibaba.fastjson.JSONObject;
import com.fsk.wechatroot.Util.MessageUtil;
import com.fsk.wechatroot.dao.InitCallback;
import com.fsk.wechatroot.dao.TextMessage;
import com.fsk.wechatroot.service.InitCallbackService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Author: fsk
 * Date: 2022/8/31 1:31
 * FileName: CheckCtroller
 * Description: 验证微信服务器接口
 */
@Slf4j
@Controller
@RequestMapping("/check")
public class CheckCtroller {

    @Autowired
    private InitCallbackService initCallbackService;

    @GetMapping("/init")
    @ResponseBody
    public String init(InitCallback initCallback){
        System.out.println(initCallback.getEchostr());
        if (!initCallback.getEchostr().equals("")){
            return initCallbackService.CheckInit(initCallback);
        }else{
            return "error";
        }
    }

    @PostMapping("/init")
    @ResponseBody
    public String Callback_text(HttpServletRequest request, HttpServletResponse response) {

        MessageUtil messageUtil=new MessageUtil();
        String message = null;
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        try {
            Map<String, String> map = messageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            Long startTime=System.currentTimeMillis();

            if (msgType.equals("text")){
                /**
                 * 文本消息回复
                 */
                String content = map.get("Content");
                TextMessage text = new TextMessage();
                text.setFromUserName(toUserName);
                text.setToUserName(fromUserName);
                text.setCreateTime(new Date().getTime()+"");
                text.setMsgType("text");
                text.setContent(initCallbackService.BackMessageText(content,fromUserName));
                message = MessageUtil.textMessageToxml(text);
                log.info("控制层message:"+message);
            }else if (msgType.equals("event")){
                /**
                * 按钮消息回复
                */
                String content = map.get("EventKey");
                TextMessage text = new TextMessage();
                text.setFromUserName(toUserName);
                text.setToUserName(fromUserName);
                text.setCreateTime(new Date().getTime()+"");
                text.setMsgType("text");
                text.setContent(initCallbackService.ButtonEvents(content,fromUserName));
                message = MessageUtil.textMessageToxml(text);
                log.info("控制层message:"+message);
            }
            Long endTime=System.currentTimeMillis();
            log.info(fromUserName+"查询时间耗时"+(endTime-startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    @GetMapping("/getAcc")
    @ResponseBody
    public JSONObject getAcc(){
        JSONObject acc=initCallbackService.GetAcc();
        return acc;
    }



}
