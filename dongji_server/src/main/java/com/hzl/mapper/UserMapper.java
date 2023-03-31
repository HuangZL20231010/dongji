package com.hzl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.dto.PersonalityLabelDTO;
import com.hzl.dto.UserDTO;
import com.hzl.entity.PersonalityLabel;
import com.hzl.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select("select * from user a,photos b where a.openid='${openid}' and b.openid='${openid}'")
    UserDTO getUserInfo1(String openid);

    @Select("select * from personality_label where openid='${openid}'")
    PersonalityLabel getUserInfo2(String openid);



}
