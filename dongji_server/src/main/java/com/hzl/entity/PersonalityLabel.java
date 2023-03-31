package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("personality_label")

public class PersonalityLabel {

    @TableId(type = IdType.AUTO)
    String id;

    String openid;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label1;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label2;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label3;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label4;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label5;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label6;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label7;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label8;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    String label9;
}
