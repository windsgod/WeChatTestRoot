package com.fsk.wechatroot.dao;

import lombok.Data;

import java.util.Date;

/**
 * Author: fsk
 * Date: 2022/9/3 11:59
 * FileName: user
 * Description: 用户类
 */
@Data
public class User {

    private int id;
    private String username;//用户微信id
    private String jdname;//京东昵称
    private String ck;//jd的cookie
    private String time;//提交ck的时间
    private int group;



}
