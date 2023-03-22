package com.hzl.utils;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GetUserInfoUtil {
    // 请求的网址
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    // appid
    public static final String WX_LOGIN_APPID = "wx95a73c45ad609c7f";    //自己的appid
    // 密匙
    public static final String WX_LOGIN_SECRET = "a08c114f04a4bc9ace48bce704f277cd";   //自己的secret
    // 固定参数
    public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";

    //通过code换取微信小程序官网获取的信息
    public static JSONObject getResultJson(String code){
        //配置请求参数
        Map<String,String> params = new HashMap<>();
        params.put("appid", WX_LOGIN_APPID);
        params.put("secret",WX_LOGIN_SECRET);
        params.put("js_code",code);
        params.put("grant_type",WX_LOGIN_GRANT_TYPE);

        //向微信服务器发送请求
        String wxRequestResult = HttpClientUtil.doGet(WX_LOGIN_URL,params);
        JSONObject resultJson = JSONObject.fromObject(wxRequestResult);

        return resultJson;
    }

    //获取openid
    public static String getOpenid(String code){
        return getResultJson(code).getString("openid");
    }

}