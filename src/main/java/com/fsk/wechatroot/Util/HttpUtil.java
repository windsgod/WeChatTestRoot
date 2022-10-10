package com.fsk.wechatroot.Util;


import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author: fsk
 * Date: 2022/8/31 18:31
 * FileName: HttpUtil
 * Description: 访问网页接口工具类
 */
@Component
public class HttpUtil {

    static public OkHttpClient client;

    static {

        client = new OkHttpClient.Builder()

                .connectTimeout(10000L, TimeUnit.MILLISECONDS)

                .readTimeout(10000L, TimeUnit.MILLISECONDS)

                .build();

    }


    public static String getAuthorization(){

        String authorization="";

        String result = "";

        Request request = new Request.Builder()

                .url("http://8.142.32.26:5701/open/auth/token?client_id=o8aplLyXP_7x&client_secret=n2693ffSApUinun4j_eNLvti")

                .get()

                .build();

        Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
            //判断是否成功
            if (response.isSuccessful()){
                result = response.body().string();
            }else {
                return "请求失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject= (JSONObject) JSONObject.parseObject(result).get("data");

        authorization="Bearer "+jsonObject.get("token");

        return authorization;

    }


    /**
     * GET请求  啥参数也不带
     */


    public static String okhttp_simple_get(String get_url){

        String result = "";

        Request request = new Request.Builder()

                .url(get_url)

                .get()

                .build();

        Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
            //判断是否成功
            if (response.isSuccessful()){
                result = response.body().string();
            }else {
                return "请求失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }



    /**
     * 同步GET请求 带Authorization认证
     */

    public static String okhttp_get(String get_url){

        String authorization=getAuthorization();

        String result = "";

        Request request = new Request.Builder()

                .url(get_url)

                .header("Authorization", authorization)

                .get()

                .build();

        Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
            //判断是否成功
            if (response.isSuccessful()){
                result = response.body().string();
            }else {
                return "请求失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 同步GET请求
     *
     * 携带Cookie的get请求
     *
     */

    public static String okhttp_get(String get_url,String Cookie){

        String str1="";
        String reg = "[^\u4e00-\u9fa5]";
        String str = Cookie.replaceAll(reg, "");

        if (str.length()>0) {
            try {
                str1= URLEncoder.encode(str,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Cookie=Cookie.replaceAll(str,str1);
        }

        System.out.println(Cookie);
        String result = "";

        Request request = new Request.Builder()

                .url(get_url)

                .header("Cookie", Cookie)

                .get()

                .build();

        Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
            //判断是否成功
            if (response.isSuccessful()){
                result = response.body().string();
            }else {
                return "请求失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }


    /**
     * 同步POST请求 带Authorization认证
     *
     * 携带ck,authorization
     */

    public static String okhttp_post(String get_url,String ck){

        String str1="";
        String reg = "[^\u4e00-\u9fa5]";
        String str = ck.replaceAll(reg, "");
        if (str.length()>0) {
            try {
                str1= URLEncoder.encode(str,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ck=ck.replaceAll(str,str1);
        }

        String authorization=getAuthorization();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String result = "";
        String requestBody = ck;

        Request request = new Request.Builder()

                .url(get_url)

                .header("Authorization", authorization)

                .post(RequestBody.create(mediaType, requestBody))

                .build();

        Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
            //判断是否成功
            if (response.isSuccessful()){
                result = response.body().string();
            }else {
                return "请求失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }




    public static String okhttp_post(String get_url, String ck, Map<String,String> map){

        String str1="";
        String reg = "[^\u4e00-\u9fa5]";
        String str = ck.replaceAll(reg, "");
        if (str.length()>0) {
            try {
                str1= URLEncoder.encode(str,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ck=ck.replaceAll(str,str1);
        }

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        String result = "";

        FormBody.Builder builder = new FormBody.Builder();
        for (String m:map.keySet()
             ) {
            builder.add(m,map.get(m));
        }


        Request request = new Request.Builder()

                .url(get_url)

                .header("Cookie", ck)

                .post(builder.build())

                .build();

        Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
            //判断是否成功
            if (response.isSuccessful()){
                result = response.body().string();
            }else {
                return "请求失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }


    /**
     *
     *
     * JS格式的Body
     */
    public static String okhttp_post(String get_url,JSONObject jsonObject){

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String result = "";
        String requestBody = jsonObject.toString();

        Request request = new Request.Builder()

                .url(get_url)

                .post(RequestBody.create(mediaType, requestBody))

                .build();

        Call call = client.newCall(request);


        Response response = null;
        try {
            response = call.execute();
            //判断是否成功
            if (response.isSuccessful()){
                result = response.body().string();
            }else {
                return "请求失败";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

}
