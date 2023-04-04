package com.hzl.controller;

import com.hzl.dto.Result;
import com.hzl.dto.ScheduleDTO;
import com.hzl.entity.Schedule;
import com.hzl.service.IScheduleService;
import com.hzl.service.ISupervisorService;
import com.hzl.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Resource
    ISupervisorService supervisorService;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 获取当天计划
     * @param request
     * @return
     */
    @GetMapping(value = "/getSupervisor")
    public Result getSupervisor(HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return supervisorService.getSupervisor(openid);

    }


}
