package com.fsk.wechatroot.dao;

import lombok.Data;

/**
 * Author: fsk
 * Date: 2022/8/31 13:30
 * FileName: TextMessage
 * Description: 文本消息实体类
 */
@Data
public class TextMessage {

    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;

}
