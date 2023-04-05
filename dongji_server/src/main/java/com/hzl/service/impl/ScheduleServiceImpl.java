package com.hzl.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.dto.Result;
import com.hzl.dto.ScheduleDTO;
import com.hzl.entity.Schedule;
import com.hzl.entity.User;
import com.hzl.mapper.ScheduleMapper;
import com.hzl.service.IScheduleService;
import com.hzl.service.ITempleteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ScheduleServiceImpl implements IScheduleService {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Override
    public Result getSchedules(Schedule schedule) {
        Date date = schedule.getDate();

        String planDate = new SimpleDateFormat("yyyy-MM-dd").format(date).toString();



        //查数据
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",schedule.getOpenid());
        queryWrapper.eq("isFinish",0);
        queryWrapper.eq("planDate",planDate);
        List<Schedule> list = scheduleMapper.selectList(queryWrapper);
        List<ScheduleDTO> scheduleDTOS = BeanUtil.copyToList(list, ScheduleDTO.class);
        return Result.ok(scheduleDTOS);

    }

    @Override
    public Result saveSchedule(ScheduleDTO scheduleDTO, String openid) {

        Schedule schedule = new Schedule();
        schedule.setOpenid(openid);
        schedule.setSport(scheduleDTO.getSport());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setTarget(scheduleDTO.getTarget());
        Date date = scheduleDTO.getDate();
        String planDate = new SimpleDateFormat("yyyy-MM-dd").format(date).toString();
        System.out.println("应该是这个格式：yyyy-MM-dd："+planDate);
        schedule.setPlanDate(planDate);
        schedule.setIsFinish(0);

        //存数据
        int ok = scheduleMapper.insert(schedule);
        if(ok<1){
            log.error("保存计划 失败");
        }

        return Result.ok();
    }

    @Override
    public Result deleteSchedule(Schedule schedule) {
        scheduleMapper.deleteById(schedule.getId());
        return Result.ok();
    }


}
