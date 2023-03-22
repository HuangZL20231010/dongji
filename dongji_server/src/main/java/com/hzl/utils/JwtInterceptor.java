package com.hzl.utils;

import com.hzl.common.TokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        log.info("被拦截了");

        //获取请求头token
        String token = request.getHeader("Authorization");

        try{
            jwtUtil.verifyToken(token); //校验token
            return true; //放行请求
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            throw new TokenException("token过期！");
        }catch (MalformedJwtException e){
            e.printStackTrace();
            throw new TokenException("token格式错误！");
        }catch (SignatureException e){
            e.printStackTrace();
            throw new TokenException("无效签名！");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new TokenException("非法请求！");
        }catch (Exception e){
            e.printStackTrace();
            throw new TokenException("token无效！");
        }
    }

}