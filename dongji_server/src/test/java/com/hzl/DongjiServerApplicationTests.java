package com.hzl;

import net.fastposter.client.FastposterClient;
import net.fastposter.client.FastposterCloudClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DongjiServerApplicationTests {

    @Autowired
    FastposterClient client;

    @Test
    void contextLoads() {

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

}
