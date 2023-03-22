package com.hzl.service;

import com.hzl.dto.Result;
import com.hzl.entity.User;


public interface IUserService  {

    /**
     * 通过openid查询用户
     * @param openid
     * @return
     */
    User queryUserInfo(String openid);

    /**
     * 添加用户
     * @param openid
     * @return
     */
    int addUser(String openid);

    /**
     * 更新用户基本信息
     * @param openid
     * @param nickname
     * @param avatarUrl
     * @return
     */
    int updateUserWxInfo(String openid, String nickname, String avatarUrl);

    Result getUserInfo();
}
