package com.hzl.service;


import com.hzl.dto.Result;
import com.hzl.dto.ScheduleDTO;
import com.hzl.entity.Schedule;

public interface IScheduleService {


    Result getSchedules(Schedule schedule);


    Result saveSchedule(ScheduleDTO scheduleDTO, String openid);
}
