package com.fsk.wechatroot.dao;

import lombok.Data;

/**
 * Author: fsk
 * Date: 2022/8/31 2:56
 * FileName: WeChat
 * Description: 测试号配置
 */
@Data
public class WeChat {

    private String appID="wx44932bb3b496e0c2";
    private String appsecret="b17d6c84e266a575f5d3effd378b6f2f";
    private String access_token;
    private String expires_in;

}
