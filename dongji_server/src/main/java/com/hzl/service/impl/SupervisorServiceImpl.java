package com.hzl.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.dto.MessageDTO;
import com.hzl.dto.Result;
import com.hzl.dto.SupervisorDTO;
import com.hzl.dto.UserDTO;
import com.hzl.entity.Group;
import com.hzl.entity.Message;
import com.hzl.entity.Partner;
import com.hzl.entity.User;
import com.hzl.mapper.SupervisorMapper;
import com.hzl.service.ISupervisorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class SupervisorServiceImpl implements ISupervisorService {

    @Autowired
    SupervisorMapper supervisorMapper;


    @Override
    public Result getSupervisor(String openid) {
        //查groupId
        Partner partner =supervisorMapper.selectPartner(openid);/////////////////////////
        String partnerId = partner.getPartnerId();
        Integer groupId = partner.getGroupId();

        //通过groupId查message
        List<Message> messageList = supervisorMapper.selectList(new QueryWrapper<Message>().eq("groupId", groupId));

        //通过groupId查group
        Group group=supervisorMapper.selectGroup(groupId);

        //通过partnerId查user
        ArrayList<User> users = supervisorMapper.selectUser(openid, partnerId);

        //整合数据
        SupervisorDTO supervisorDTO = new SupervisorDTO();

        ArrayList<MessageDTO> messageDTOArrayList = new ArrayList<MessageDTO>();
        for (int i = 0; i < messageList.size(); i++) {
            Message message = messageList.get(i);
            MessageDTO messageDTO = BeanUtil.toBean(message, MessageDTO.class);
            if(message.getSendUserId().equals(openid)){
                messageDTO.setSender("me");
            }else{
                messageDTO.setSender("partner");
            }
            messageDTOArrayList.add(messageDTO);
        }
        supervisorDTO.setMessageDTO(messageDTOArrayList);

        supervisorDTO.setIntegral(group.getIntegral());

        Timestamp beginDay = group.getBeginDay();
        long beginDayTime = beginDay.getTime();
        long nowTime = new Timestamp(new Date().getTime()).getTime();
        long togetherDay = (nowTime - beginDayTime)/(1000*60*60*24);
        System.out.println(togetherDay);
        supervisorDTO.setTogetherDay((int) togetherDay);

        User user1 = users.get(0);
        System.out.println(user1.getOpenid());
        if(user1.getOpenid().equals(openid)){
            System.out.println("me:"+user1);
            HashMap<String, String> me = new HashMap<>();
            me.put("avatarUrl",user1.getAvatarUrl());
            me.put("nickname",user1.getNickname());
            supervisorDTO.setMe(me);

            User user2 = users.get(1);
            HashMap<String, String> partnerr = new HashMap<>();
            partnerr.put("avatarUrl",user2.getAvatarUrl());
            partnerr.put("nickname",user2.getNickname());
            supervisorDTO.setPartner(partnerr);

        }else {
            System.out.println("partnerr:"+user1);
            HashMap<String, String> partnerr = new HashMap<>();
            partnerr.put("avatarUrl",user1.getAvatarUrl());
            partnerr.put("nickname",user1.getNickname());
            supervisorDTO.setPartner(partnerr);

            User user2 = users.get(1);
            HashMap<String, String> me = new HashMap<>();
            me.put("avatarUrl",user2.getAvatarUrl());
            me.put("nickname",user2.getNickname());
            supervisorDTO.setMe(me);
        }

        return Result.ok(supervisorDTO);

    }
}
