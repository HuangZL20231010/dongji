package com.hzl.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import net.sf.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "markerhub.jwt")
public class JwtUtil {

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成jwt token
     * @param session
     * @return
     */
    public String getToken(String session){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(session)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }


    /**
     * 从token中获取自定义登录态session后解密获取openid
     * @param token
     * @return
     */
    public String getOpenidFromToken(String token){
        String openid;
        String session;
        try{
            //解析token获取session
            Claims cliams = getCliamByToken(token);
            session = cliams.getSubject();
            //解密session
            EncryptUtil encryptUtil = new EncryptUtil();
            String jsonString = encryptUtil.decrypt(session);
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            openid = jsonObject.getString("openid");
            return openid;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */
    public Claims getCliamByToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public void verifyToken(String token){
        //在拦截器抛出异常
        Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}