package com.hzl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.dto.PersonalityLabelDTO;
import com.hzl.dto.UserDTO;
import com.hzl.entity.PersonalityLabel;
import com.hzl.entity.User;
import org.apache.ibatis.annotations.Insert;
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


    @Insert("insert personality_label(openid) values ('${openid}')")
    void insertPersonalityLabel(String openid);

    @Insert("insert interest(openid,running,skipping,football,basketball,tennis,table_tennis,yoga,bodybuilding,swimming) values ('${openid}',0,0,0,0,0,0,0,0,0)")
    void insertInterest(String openid);

    @Insert("insert photos(openid) values ('${openid}')")
    void insertPhotos(String openid);
}
