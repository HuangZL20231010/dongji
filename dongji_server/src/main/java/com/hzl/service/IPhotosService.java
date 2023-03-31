package com.hzl.service;


import com.hzl.dto.Result;

public interface IPhotosService {


    Result uploadPhoto(String openid, String photoUrl, int number);
}
