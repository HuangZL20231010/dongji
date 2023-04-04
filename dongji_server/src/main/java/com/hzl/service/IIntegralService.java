package com.hzl.service;


import com.hzl.entity.User;

public interface IIntegralService {


    void updateIntegral(User user);

    void updatePosterNum(String openid);
}
