package com.hzl.service;


import com.hzl.dto.Poster;
import com.hzl.dto.Result;

public interface IPostService {


    Result makePoster(Poster poster, String openid);
}
