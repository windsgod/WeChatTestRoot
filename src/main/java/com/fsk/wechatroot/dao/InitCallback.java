package com.fsk.wechatroot.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: fsk
 * Date: 2022/8/31 1:09
 * FileName: InitCallback
 * Description: 接受微信测试接口验证的对象
 */
@Data
public class InitCallback {

    private  String token="fsk0425";
    private  String signature;
    private  String timestamp;    //时间戳
    private  String nonce;       //随机数
    private  String echostr;	//随机字符串

}
