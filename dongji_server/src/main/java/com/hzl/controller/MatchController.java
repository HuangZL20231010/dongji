package com.hzl.controller;

import com.hzl.config.interceptor.NoNeedAuthorization;
import com.hzl.dto.MatchDTO;
import com.hzl.dto.Result;
import com.hzl.entity.Schedule;
import com.hzl.service.IMatchService;
import com.hzl.service.IScheduleService;
import com.hzl.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/match")
@Slf4j
public class MatchController {

    @Resource
    IMatchService matchService;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 获取兴趣（填充小气泡）
     * @param request
     * @return
     */
    @GetMapping(value = "/getInterest")
    public Result getInterest(HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return matchService.getInterest(openid);
    }

    /**
     * 获取匹配到的人
     * @param request
     * @return
     */
    @GetMapping(value = "/getInvitee")
    public Result getInvitee(HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return matchService.getInvitee(openid);
    }

    /**
     * 获取邀请我的人
     * @param request
     * @return
     */
    @GetMapping(value = "/getInviter")
    public Result getInviter(HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return matchService.getInviter(openid);
    }

    /**
     * 发送组队邀请
     * @param matchDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/sendInvite")
    public Result sendInvite(@RequestBody MatchDTO matchDTO, HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return matchService.sendInvite(matchDTO,openid);
    }

    /**
     * 接受邀请
     * @param matchDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/acceptInvite")
    public Result acceptInvite(@RequestBody MatchDTO matchDTO, HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return matchService.acceptInvite(matchDTO,openid);
    }

    /**
     * 拒绝邀请
     * @param matchDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/refuseInvite")
    public Result refuseInvite(@RequestBody MatchDTO matchDTO, HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return matchService.refuseInvite(matchDTO,openid);
    }

    /**
     * 获得用户是否匹配的状态
     * @param request
     * @return
     */
    @GetMapping(value = "/getIsMatched")
    public Result getIsMatched(HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return matchService.getIsMatched(openid);
    }

}
