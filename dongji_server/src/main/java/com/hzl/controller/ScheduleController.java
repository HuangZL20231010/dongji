package com.hzl.controller;

import cn.hutool.json.JSONObject;
import com.google.gson.JsonObject;
import com.hzl.config.interceptor.NoNeedAuthorization;
import com.hzl.dto.Result;
import com.hzl.dto.ScheduleDTO;
import com.hzl.entity.Schedule;
import com.hzl.service.IScheduleService;
import com.hzl.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Resource
    IScheduleService scheduleService;

    @Autowired
    JwtUtil jwtUtil;

    /**
     * 获取当天计划
     * @param schedule
     * @param request
     * @return
     */
    @PostMapping(value = "/getSchedules")
    public Result getSchedules(@RequestBody Schedule schedule, HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        schedule.setOpenid(openid);

        return scheduleService.getSchedules(schedule);
    }

    /**
     * 保存计划
     * @param scheduleDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/saveSchedule")
    public Result saveSchedule(@RequestBody ScheduleDTO scheduleDTO, HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        return scheduleService.saveSchedule(scheduleDTO,openid);
    }

    /**
     * 删除功能
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/deleteSchedule/{id}")
    public Result deleteSchedule(@PathVariable int id,HttpServletRequest request)  {
        //获取请求头token
        String token = request.getHeader("Authorization");
        //从token中获取openid
        String openid = jwtUtil.getOpenidFromToken(token);

        System.out.println(id);
        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setOpenid(openid);

        return scheduleService.deleteSchedule(schedule);
    }



}
