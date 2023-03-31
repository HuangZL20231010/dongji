package com.hzl.service;


import com.hzl.dto.Result;
import com.hzl.entity.Interest;

public interface IInterestService {



    Result saveSports(Interest interest);

    Result getSports(String openid);
}
