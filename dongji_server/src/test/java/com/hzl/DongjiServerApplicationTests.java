package com.hzl;

import cn.hutool.core.util.IdUtil;
import com.hzl.dto.MatchDTO;
import com.hzl.dto.Result;
import com.hzl.entity.User;
import com.hzl.service.IMatchService;
import com.hzl.service.ISupervisorService;
import com.hzl.service.IUserService;
import com.hzl.utils.EncryptUtil;
import net.fastposter.client.FastposterClient;
import net.fastposter.client.FastposterCloudClient;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DongjiServerApplicationTests {

    @Autowired
    FastposterClient client;

    @Test
    void fastPoster() {

        // 2.准备海报参数
        Map<String, Object> params = new HashMap<>();
        params.put("day", "09");
        params.put("yearAndMonth", "2023.12");
        params.put("target", "13.14");
        params.put("head1", "距离（公里）");
        params.put("head2", "运动时间");
        params.put("time", "23:34");
        params.put("message", "今天超级开心！！！！");
        params.put("sport", "游泳");



        // 3.生成海报并保存
        String url = client.buildPoster("2f5cdfcdaac9f081").params(params).build().save();

        System.out.println(url);
    }

    @Test
    void test(){
        // 1.创建Fastposter客户端对象
        FastposterCloudClient client = FastposterCloudClient.builder().appKey("e3d59b071b524017").appSecret("12a35254d6554bb7b02a3bef39c78116").build();

        // 2.准备海报参数（从实际业务中获取）
        Map<String, Object> params = new HashMap<>();
        params.put("date", "2001.1.1");
        params.put("sport", "运动ing");


        // 3.生成海报并保存(提供图片流、二进制、base64等多种方式)
        String save = client.buildPoster("5ffceb71800849a3").params(params).build().save();

        System.out.println(save);

    }

    @Resource
    IUserService userService;


    /**
     * 新增用户
     */
    @Test
    void insertNewUser(){
        //创建10个
        for (int i = 0; i < 10; i++) {
            String openid= IdUtil.simpleUUID();
            int rs = userService.addUser(openid);
            System.out.println(openid);
        }


    }


    @Autowired
    IMatchService matchService;
    /**
     * 匹配
     */
    @Test
    void getInvitee(){
        String openid="ceshi-------123456789";
        Result invitee = matchService.getInvitee(openid);
        System.out.println(invitee);
    }

    @Test
    void sendInvite(){
        String openid="ceshi-------123456789";
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setInviteeId("5402887b28ef46a8b833517a3b6bd47e");
        Result result = matchService.sendInvite(matchDTO, openid);
        System.out.println(result);
    }

    @Test
    void getInviter(){
        String openid="oYMXk4iSQlNKFSd59_QB1Ucx4ato";
        Result result = matchService.getInviter(openid);
        System.out.println(result);
    }


    @Test
    void getInterest(){
        String openid="oYMXk4iSQlNKFSd59_QB1Ucx4ato";
        Result result = matchService.getInterest(openid);
        System.out.println(result);
    }


    @Test
    void refuseInvite(){
        String openid="oYMXk4iSQlNKFSd59_QB1Ucx4ato";
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setInviterId("27e213dc502e4fb18b4c04d9621bfe77");
        Result result = matchService.refuseInvite(matchDTO,openid);
        System.out.println(result);
    }

    @Test
    void acceptInvite(){
        String openid="42b58641eb4f490598133ca7d88d28c0";
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setInviterId("ceshi-------123456789");
        Result result = matchService.acceptInvite(matchDTO,openid);
        System.out.println(result);
    }


    @Resource
    ISupervisorService supervisorService;
    @Test
    void supervisor(){
        String openid="oYMXk4iSQlNKFSd59_QB1Ucx4ato";
        Result result = supervisorService.getSupervisor(openid);
        System.out.println(result);
    }

    //测试登录

    @Test
    void testLogin(){
        String openid = "oYMXk4iSQlNKFSd59_QB1Ucx4ato";

        //生成token
        String token = "jdisajfsjfksjfajklfa";
        Map<String,String> result = new HashMap<>();
        result.put("token", token);

        //查询用户是否存在
        User user = userService.queryUserInfo(openid);
        if (user != null){
            result.put("ismatch",user.getIs_match()+"");
            System.out.println("1"+result);
        }else { //用户不存在，新建用户信息
            int rs = userService.addUser(openid);
            if (rs <= 0){
                System.out.println("创建新用户失败");
            }
            result.put("ismatch",0+"");
            System.out.println(result);
        }
    }

}
