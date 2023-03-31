package com.hzl.service;

import com.hzl.dto.PersonalityLabelDTO;
import com.hzl.dto.Result;

import java.util.List;

public interface IPersonalityLabelService {


    Result savePersonalityLabel(String openid, List<PersonalityLabelDTO> labels);

}
