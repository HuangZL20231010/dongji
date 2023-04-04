package com.hzl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.dto.MatchDTO;
import com.hzl.entity.Interest;
import com.hzl.entity.Match;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;


@Mapper
public interface MatchMapper extends BaseMapper<Match> {


    @Select("select * from interest where openid='${openid}'")
    Interest getInterest(String openid);

    @Select("select * from user a,(select * from interest where interest.openid!='${openid}' limit 0,50) b where a.openid=b.openid and is_match=0 ")
    ArrayList<Interest> selectTop100(String openid);

    @Select("select * from user where openid='${openid}'")
    MatchDTO getAvatarAndNickname(String openid);


    @Update("update user set is_match=1 where openid='${openid}' or openid='${inviterOpenid}' ")
    void updateUserIsMatched(String openid, String inviterOpenid);
}
