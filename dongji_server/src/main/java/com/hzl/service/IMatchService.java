package com.hzl.service;


import com.hzl.dto.MatchDTO;
import com.hzl.dto.Result;

public interface IMatchService {


    Result getInterest(String openid);

    Result getInvitee(String openid);

    Result getInviter(String openid);

    Result sendInvite(MatchDTO matchDTO, String openid);

    Result acceptInvite(MatchDTO matchDTO, String openid);

    Result refuseInvite(MatchDTO matchDTO, String openid);

    Result getIsMatched(String openid);
}
