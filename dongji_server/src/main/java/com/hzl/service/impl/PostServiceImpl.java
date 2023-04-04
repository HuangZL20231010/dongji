package com.hzl.service.impl;


import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hzl.dto.Poster;
import com.hzl.dto.Result;
import com.hzl.entity.Message;
import com.hzl.entity.Schedule;
import com.hzl.entity.User;
import com.hzl.mapper.ScheduleMapper;
import com.hzl.mapper.SupervisorMapper;
import com.hzl.mapper.UserMapper;
import com.hzl.service.IOssService;
import com.hzl.service.IPostService;
import com.hzl.service.ITempleteService;
import com.hzl.utils.FastPoster;
import lombok.extern.slf4j.Slf4j;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    FastPoster fastPoster;

    @Autowired
    IOssService ossService;

    @Autowired
    SupervisorMapper supervisorMapper;

    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    IntegralServiceImpl integralService;

    @Autowired
    UserMapper userMapper;


    @Override
    public Result makePoster(Poster poster, String openid) {
        //制作海报
        // 创建日期格式化对象，指定日期格式为"dd"和"yyyy.MM"
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy.MM");

        // 获取当前日期
        Date currentDate = new Date();

        // 格式化日期
        String day = dayFormat.format(currentDate);
        String month = monthFormat.format(currentDate);
        poster.setDay(day);
        poster.setYearAndMonth(month);
        poster.setHead1("达成目标");
        poster.setHead2("运动时间");


        String localUrl = fastPoster.generatePost(poster);
        System.out.println("localUrl:"+localUrl);

        //将海报上传到云端
        File file = new File(localUrl);
        MultipartFile multipartFile;
        try {
            multipartFile = new MockMultipartFile("file",
                    file.getName(), "image/jpeg", Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = ossService.upload(multipartFile);

        //设置这个schedule完成 isFinish=1
//        UpdateWrapper<Schedule> updateWrapper1 = new UpdateWrapper<>();
        Schedule schedule = new Schedule();
        schedule.setId(poster.getScheduleId());
        schedule.setIsFinish(1);
        scheduleMapper.updateById(schedule);

        //先要判断是否匹配了
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("openid", openid));
        if (user.getIs_match()==0)return Result.ok(url);

        //让group的posterNum加1
        integralService.updatePosterNum(openid);


        //将海报链接存到数据库 表message
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String nowTime = formatter.format(date);

        int ok = supervisorMapper.insertMessage(openid,nowTime,"poster",url,"");
        if(ok<1){
            Result.fail("保存海报失败");
        }


        return Result.ok(url);
    }



}
