package com.hzl.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hzl.entity.Group;
import com.hzl.entity.Partner;
import com.hzl.entity.User;
import com.hzl.mapper.GroupMapper;
import com.hzl.mapper.SupervisorMapper;
import com.hzl.mapper.UserMapper;
import com.hzl.service.IIntegralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Slf4j
@Service
public class IntegralServiceImpl implements IIntegralService {

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    SupervisorMapper supervisorMapper;

    @Autowired
    UserMapper userMapper;


    @Override
    public void updateIntegral(User user) {
        System.out.println("正在更新积分");
        //得到group
        //查groupId
        Partner partner =supervisorMapper.selectPartner(user.getOpenid());/////////////////////////
        String partnerId = partner.getPartnerId();
        Integer groupId = partner.getGroupId();
        Group group = supervisorMapper.selectGroup(groupId);////////

        //计算里面的日期*5 和 posterNum*10 得到积分
        Timestamp beginDay = group.getBeginDay();
        long beginDayTime = beginDay.getTime();
        long nowTime = new Timestamp(new Date().getTime()).getTime();
        long togetherDay = (nowTime - beginDayTime)/(1000*60*60*24);
        Integer posterNum = group.getPosterNum();
        Integer oldIntegral = group.getIntegral();
        int newIntegral = (int) (togetherDay*5+posterNum*10);
        //积分规则 100分解锁学校  200分解锁性别 250分解锁label1 300分解锁label2
        if(oldIntegral<100&newIntegral>=100){
            supervisorMapper.insertMessage(user.getOpenid(),"积分达到100分，解锁[学校]信息","text","",user.getSchool());//////
            User user2=userMapper.selectOne(new QueryWrapper<User>().eq("openid",partnerId));
            supervisorMapper.insertMessage(user2.getOpenid(),"积分达到100分，解锁学校信息","text","",user2.getSchool());/////
        }else if(oldIntegral<200&newIntegral>=200){
            supervisorMapper.insertMessage(user.getOpenid(),"积分达到200分，解锁[性别]信息","text","",user.getGender());
            User user2=userMapper.selectOne(new QueryWrapper<User>().eq("openid",partnerId));
            supervisorMapper.insertMessage(user2.getOpenid(),"积分达到200分，解锁[性别]信息","text","",user2.getGender());
        }

        //更新积分
        if(oldIntegral<newIntegral){
            group.setIntegral(newIntegral);
            groupMapper.update(group,new UpdateWrapper<Group>().eq("groupId",groupId));/////
        }





    }

    @Override
    public void updatePosterNum(String openid) {
        //获得user
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("openid", openid));

        //得到group
        //查groupId
        Partner partner =supervisorMapper.selectPartner(openid);/////////////////////////
        String partnerId = partner.getPartnerId();
        Integer groupId = partner.getGroupId();
        Group group = supervisorMapper.selectGroup(groupId);///

        //计算里面的日期*5 和 posterNum*10 得到积分
        Timestamp beginDay = group.getBeginDay();
        long beginDayTime = beginDay.getTime();
        long nowTime = new Timestamp(new Date().getTime()).getTime();
        long togetherDay = (nowTime - beginDayTime)/(1000*60*60*24);
        int posterNum = group.getPosterNum()+1;
        Integer oldIntegral = group.getIntegral();
        int newIntegral = (int) (togetherDay*5+posterNum*10);
        //积分规则 100分解锁学校  200分解锁性别 250分解锁label1 300分解锁label2
        if(oldIntegral<100&newIntegral>=100){
            supervisorMapper.insertMessage(user.getOpenid(),"积分达到100分，解锁[学校]信息","text","",user.getSchool());//////
            User user2=userMapper.selectOne(new QueryWrapper<User>().eq("openid",partnerId));
            supervisorMapper.insertMessage(user2.getOpenid(),"积分达到100分，解锁学校信息","text","",user2.getSchool());/////
        }else if(oldIntegral<200&newIntegral>=200){
            supervisorMapper.insertMessage(user.getOpenid(),"积分达到200分，解锁[性别]信息","text","",user.getGender());
            User user2=userMapper.selectOne(new QueryWrapper<User>().eq("openid",partnerId));
            supervisorMapper.insertMessage(user2.getOpenid(),"积分达到200分，解锁[性别]信息","text","",user2.getGender());
        }

        group.setPosterNum(posterNum);
        group.setIntegral(newIntegral);
        groupMapper.update(group,new UpdateWrapper<Group>().eq("groupId",groupId));/////

    }


}
