package com.hzl.dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.hzl.entity.PersonalityLabel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@AllArgsConstructor
@Data
public class UserDTO {

    private String nickname;

    private String avatarUrl;

    private String gender;

    private String school;

    private String college;

    private String description;

    private String birthday;

    private String photo1url;

    private String photo2url;

    private int labelsSize;

    private Integer is_match;

    private ArrayList<PersonalityLabelDTO> labels;

    UserDTO(){
//        labels=new PersonalityLabelDTO[9];
        labels=new ArrayList<PersonalityLabelDTO>();
        labelsSize=0;
    }

    public void setPersonalityLabelDTO(PersonalityLabelDTO dto){
        labels.add(dto);
        labelsSize++;
    }

}
