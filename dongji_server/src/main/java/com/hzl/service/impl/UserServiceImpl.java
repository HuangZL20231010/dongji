package com.hzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzl.dto.Result;
import com.hzl.entity.User;
import com.hzl.mapper.UserMapper;
import com.hzl.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserInfo(String openid) {
//        return userMapper.getUserByOpenid(openid);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);

        List<User> users = userMapper.selectList(wrapper);

        if (users.isEmpty())return null;
        else return users.get(0);
    }

    @Override
    public int addUser(String openid) {
        User user = new User();
        user.setOpenid(openid);
        return userMapper.insert(user);
    }

    @Override
    public int updateUserWxInfo(String openid, String nickname, String avatarUrl) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("openid",openid);
        User user = new User();
        user.setOpenid(openid);
        user.setNickname(nickname);
        user.setAvatarUrl(avatarUrl);

        return userMapper.update(user,updateWrapper);

    }

    @Override
    public Result getUserInfo() {
        return null;
    }

}
