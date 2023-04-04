package com.hzl.service.impl;

import cn.hutool.core.lang.hash.Hash;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzl.dto.PersonalityLabelDTO;
import com.hzl.dto.Result;
import com.hzl.dto.UserDTO;
import com.hzl.entity.PersonalityLabel;
import com.hzl.entity.User;
import com.hzl.mapper.UserMapper;
import com.hzl.service.IIntegralService;
import com.hzl.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IIntegralService integralService;

    @Override
    public User queryUserInfo(String openid) {
//        return userMapper.getUserByOpenid(openid);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);

        List<User> users = userMapper.selectList(wrapper);

        if (users.isEmpty())return null;
        else {
            User user = users.get(0);
            if(user.getIs_match()==1){
                //先执行积分更新任务
                integralService.updateIntegral(user);
            }

            return user;
        }
    }

    @Override
    public int addUser(String openid) {
        User user = new User();
        user.setOpenid(openid);
        user.setIs_match(0);
        user.setNickname("我想改名");
        user.setDescription("我运动，我快乐~");
        user.setAvatarUrl("https://huangzelin.oss-cn-beijing.aliyuncs.com/dongji/images/2023-03-31f41dac0bc4d44068b9618f316dbb65085a60e1aee2489001a8c53d956f71c61.jpg");

        //同时创建personalityLabel，interest，photos
        userMapper.insertPersonalityLabel(openid);
        userMapper.insertInterest(openid);
        userMapper.insertPhotos(openid);

        return userMapper.insert(user);
    }

    @Override
    public int uploadAvatar(String openid, String avatarUrl) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("openid",openid);
        User user = new User();
        user.setOpenid(openid);
        user.setAvatarUrl(avatarUrl);

        return userMapper.update(user,updateWrapper);
    }


    @Override
    public Result getUserInfo(String openid) {
        //设置一些用户没有的标签，供用户选
        HashSet<String> set = new HashSet<String>();
        set.add("旅游");
        set.add("美食");
        set.add("音乐");
        set.add("电影");
        set.add("阅读");
        set.add("游戏");
        set.add("编程");
        set.add("健身");
        set.add("考研党");
        set.add("工作狂");
        int count=9;//计算需要提供几个

        //从数据库中调取涉及表：user,photo,personalityLabel
        //先从user表和photos表取数据
        UserDTO userInfo = userMapper.getUserInfo1(openid);

        //再从personalityLabel取数据
        PersonalityLabel personalityLabel= userMapper.getUserInfo2(openid);
        System.out.println(personalityLabel);
        //将personalityLabel里的数据整合到userInfo中
        if(personalityLabel.getLabel1()!=null){
            set.remove(personalityLabel.getLabel1());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel1(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel2()!=null){
            set.remove(personalityLabel.getLabel2());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel2(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel3()!=null){
            set.remove(personalityLabel.getLabel3());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel3(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel4()!=null){
            set.remove(personalityLabel.getLabel4());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel4(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel5()!=null){
            set.remove(personalityLabel.getLabel5());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel5(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel6()!=null){
            set.remove(personalityLabel.getLabel6());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel6(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel7()!=null){
            set.remove(personalityLabel.getLabel7());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel7(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel8()!=null){
            set.remove(personalityLabel.getLabel8());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel8(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }
        if(personalityLabel.getLabel9()!=null){
            set.remove(personalityLabel.getLabel9());
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(personalityLabel.getLabel9(),"orange",true);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }


        Iterator<String> iterator = set.iterator();
        while (count!=0){
            String label = iterator.next();
            PersonalityLabelDTO labelDTO = new PersonalityLabelDTO(label,"orange",false);
            userInfo.setPersonalityLabelDTO(labelDTO);
            count--;
        }


        return Result.ok(userInfo);
    }


    @Override
    public Result saveUserInfo(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        String openid = user.getOpenid();
        updateWrapper.eq("openid",openid);
        int isOk = userMapper.update(user, updateWrapper);
        if(isOk==0){
            log.error("基本信息存入失败");
            return Result.fail("基本信息存入失败");
        }
        return Result.ok();

    }

}
