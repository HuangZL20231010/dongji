package com.hzl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityLabelDTO {

    //标签名
    String name;

    //标签颜色
    String color;

    //是否存在
    Boolean active;
}
