package com.hzl.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.hzl.config.interceptor.NoNeedAuthorization;
import com.hzl.dto.PersonalityLabelDTO;
import com.hzl.dto.Result;
import com.hzl.entity.Interest;
import com.hzl.entity.User;
import com.hzl.service.IInterestService;
import com.hzl.service.IPersonalityLabelService;
import com.hzl.service.IPhotosService;
import com.hzl.service.IUserService;
import com.hzl.utils.EncryptUtil;
import com.hzl.utils.GetUserInfoUtil;
import com.hzl.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Autowired
    private OssController ossController;

    @Resource
    private IPersonalityLabelService personalityLabelService;

    @Resource
    private IInterestService interestService;

    @Resource
    private IPhotosService photosService;

    @Autowired
    JwtUtil jwtUtil;

//    @ApiOperation("微信授权登录")

    /**
     * 微信授权登录
     * @param code
     * @return
     */
    @PostMapping("/login")
    @NoNeedAuthorization
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
                result.put("ismatch",user.getIs_match()+"");
                return Result.ok(result); //用户存在，返回结果
            }else { //用户不存在，新建用户信息
                int rs = userService.addUser(openid);
                if (rs <= 0){
                    return Result.fail("登录失败");
                }
                result.put("ismatch",0+"");
                return Result.ok(result);
            }
        }

        return Result.fail("授权失败"+ resultJson.getString("errmsg"));
    }

    /**
     * 存储用户头像
     * @param file
     * @param request
     * @return
     */
    //TODO 未测试
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file,
                                   HttpServletRequest request){

        //将file存到阿里云oss中,获得url
        log.info("去上传照片");
        String avatarUrl = ossController.upload(file);
        log.info("上传成功："+avatarUrl);
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        int result = userService.uploadAvatar(openid, avatarUrl);
        if(result <= 0){
            return Result.fail("更新失败！");
        }

        return Result.ok();
    }


    /**
     * 保存用户基本信息
     * @param object
     * @param request
     * @return
     */
    @PostMapping("/saveUserInfo")
    public Result saveUserInfo(@RequestBody cn.hutool.json.JSONObject object, HttpServletRequest request){
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        System.out.println(object);

        User user = JSONUtil.toBean(object, User.class);

        user.setOpenid(openid);

        return userService.saveUserInfo(user);
    }




    /**
     * 用户个性标签保存
     * @param jsonArray
     * @param request
     * @return
     */
    //TODO 未测试
    @PostMapping("/savePersonalityLabel")
    public Result savePersonalityLabel(@RequestBody JSONArray jsonArray, HttpServletRequest request){
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        List<PersonalityLabelDTO> list = JSONUtil.toList(jsonArray, PersonalityLabelDTO.class);
        System.out.println(list);

        return personalityLabelService.savePersonalityLabel(openid,list);
    }

    /**
     * 保存兴趣
     * @param object
     * @param request
     * @return
     */
    @PostMapping("/saveSports")
    public Result saveSports(@RequestBody cn.hutool.json.JSONObject object, HttpServletRequest request){
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        //解析json
//        System.out.println(object);

        Interest interest = JSONUtil.toBean(object, Interest.class);
//        System.out.println(interest);

        interest.setOpenid(openid);


        return interestService.saveSports(interest);
    }


    /**
     * 用户兴趣资料加载
     * @param request
     * @return
     */
    @GetMapping("/getSports")
    public Result getSports( HttpServletRequest request){
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);


        return interestService.getSports(openid);
    }


    /**
     * 获取用户基本信息
     * @param request
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);



        return userService.getUserInfo(openid);
    }


    /**
     * 上传精选图片
     * @param file
     * @param number
     * @param request
     * @return
     */
    @PostMapping("/uploadPhoto")
    public Result uploadPhoto( @RequestParam("file") MultipartFile file,
                               @RequestParam("number")int number,
                               HttpServletRequest request){
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        //将file存到阿里云oss中,获得url
        log.info("去上传照片");
        String photoUrl = ossController.upload(file);
        log.info("上传成功："+photoUrl);


        return photosService.uploadPhoto(openid,photoUrl,number);
    }


}
