package com.hzl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.hzl.dto.InterestDTO;
import com.hzl.dto.Result;
import com.hzl.entity.Interest;
import com.hzl.entity.User;
import com.hzl.mapper.InterestMapper;

import com.hzl.service.IInterestService;
import com.hzl.service.IPersonalityLabelService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class InteresServiceImpl implements IInterestService {


    @Autowired
    InterestMapper interestMapper;

    @Override
    public Result saveSports(Interest interest) {
        //更改数据库对应的值
        UpdateWrapper<Interest> updateWrapper = new UpdateWrapper<>();
        String openid = interest.getOpenid();
        updateWrapper.eq("openid",openid);
        int isOk = interestMapper.update(interest, updateWrapper);
        if(isOk==0){
            log.error("兴趣（运动）存入失败");
            return Result.fail("兴趣（运动）存入失败");
        }
        return Result.ok();

    }

    @Override
    public Result getSports(String openid) {
        QueryWrapper<Interest> interestQueryWrapper=new QueryWrapper<>();
        interestQueryWrapper.eq("openid",openid);

        Interest interest = interestMapper.selectOne(interestQueryWrapper);

        InterestDTO interestDTO = BeanUtil.toBean(interest, InterestDTO.class);


        return Result.ok(interestDTO);
    }
}
