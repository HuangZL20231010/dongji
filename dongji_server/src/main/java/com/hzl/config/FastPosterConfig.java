package com.hzl.config;

import net.fastposter.client.FastposterClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastPosterConfig {



    @Bean
    public FastposterClient fastposterClient(){
        // 1.创建海报客户端对象
        return   FastposterClient.builder()
                .endpoint("http://8.130.92.141:5000")      // 设置接入端点
                .token("ApfrIzxCoK1DwNZOEJCwlrnv6QZ0PCdv")  // 设置token
                .build();
    }


}
