package com.hzl.controller;

import com.hzl.dto.Poster;
import com.hzl.dto.Result;
import com.hzl.entity.Schedule;
import com.hzl.service.IPostService;
import com.hzl.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/poster")
public class PosterController {

    @Autowired
    JwtUtil jwtUtil;

    @Resource
    IPostService postService;

    /**
     * 生成海报
     * @param poster
     * @param request
     * @return
     */
    @PostMapping(value = "/makePoster")
    public Result makePoster(@RequestBody Poster poster, HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        System.out.println(poster);

        return postService.makePoster(poster,openid);


    }

}
