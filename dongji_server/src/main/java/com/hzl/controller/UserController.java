package com.hzl.controller;


import com.hzl.dto.Result;
import com.hzl.entity.User;
import com.hzl.service.IUserService;
import com.hzl.utils.EncryptUtil;
import com.hzl.utils.GetUserInfoUtil;
import com.hzl.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Autowired
    JwtUtil jwtUtil;

//    @ApiOperation("微信授权登录")
    @PostMapping("/login")
    public Result authorizeLogin( @RequestParam("code") String code) {

        //通过code换取信息
        JSONObject resultJson = GetUserInfoUtil.getResultJson(code);

        if (resultJson.has("openid")){
            //获取sessionKey和openId
            String sessionKey = resultJson.get("session_key").toString();
            String openid = resultJson.get("openid").toString();
            log.info("openid："+openid);

            //生成自定义登录态session
            String session = null;
            Map<String,String> sessionMap = new HashMap<>();

            sessionMap.put("sessionKey",sessionKey);
            sessionMap.put("openid",openid);
            session = JSONObject.fromObject(sessionMap).toString();

            //加密session
            try {
                EncryptUtil encryptUtil = new EncryptUtil();
                session = encryptUtil.encrypt(session);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //生成token
            String token = jwtUtil.getToken(session);
            Map<String,String> result = new HashMap<>();
            result.put("token", token);

            //查询用户是否存在
            User user = userService.queryUserInfo(openid);
            if (user != null){
                return Result.ok(result); //用户存在，返回结果
            }else { //用户不存在，新建用户信息
                int rs = userService.addUser(openid);
                if (rs <= 0){
                    return Result.fail("登录失败");
                }
                return Result.ok( result);
            }
        }

        return Result.fail("授权失败"+ resultJson.getString("errmsg"));
    }

//    @ApiOperation("存储用户个人信息")
    @PostMapping("/person-info")
    public Result insertPersonInfo(@RequestParam("nickname") String nickname,
                                   @RequestParam("avatarUrl") String avatarUrl,
                                   HttpServletRequest request){

        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        int result = userService.updateUserWxInfo(openid, nickname, avatarUrl);
        if(result <= 0){
            return Result.fail("更新失败！");
        }

        return Result.ok();
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(){
        return userService.getUserInfo();
    }
}
