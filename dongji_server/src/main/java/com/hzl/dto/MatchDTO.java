package com.hzl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {

    //虚拟id
   String InviteeId;

    //虚拟id
    String InviterId;

   //头像地址
   String avatarUrl;

   //昵称
    String nickname;

    //匹配了没有
    Boolean success;
}
