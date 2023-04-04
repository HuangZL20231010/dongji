package com.hzl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.dto.UserDTO;
import com.hzl.entity.Group;
import com.hzl.entity.Message;
import com.hzl.entity.Partner;
import com.hzl.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;


@Mapper
public interface SupervisorMapper extends BaseMapper<Message> {


    @Select("select * from partner where openid='${openid}'")
    Partner selectPartner(String openid);

    @Select("select * from `group` where groupId='${groupId}'")
    Group selectGroup(Integer groupId);

    @Select("select * from user where openid='${openid}' or openid='${partnerId}'")
    ArrayList<User> selectUser(String openid, String partnerId);

    @Insert("INSERT into message (sendUserId,groupId,title,type,posterUrl,content) " +
            "SELECT '${openid}',groupId,'${title}','${type}','${posterUrl}','${content}' FROM partner WHERE openid='${openid}'")
    int insertMessage(String openid, String title, String type,String posterUrl,String content);
}
