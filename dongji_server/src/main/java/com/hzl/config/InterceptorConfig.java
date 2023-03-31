package com.hzl.config;

import com.hzl.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor getJwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtInterceptor())
                .addPathPatterns("/**"); //拦截所有用户接口
//                .excludePathPatterns("/user/login","/user/insertAvatarAndNickname");//登录、验证码接口不拦截
        //换成注解模式了 只需要@NoNeedAuthorization 就能通过拦截器
    }
}
