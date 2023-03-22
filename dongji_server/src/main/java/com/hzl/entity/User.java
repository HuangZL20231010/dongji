package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String openid;

    private String nickName;

    private String avatarUrl;

    private String gender;

    private String school_name;

    private String major;

    private String personal_signature;

    private String birthday;

    private String is_match;





//    /**
//     * 主键
//     */
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//    /**
//     * 手机号码
//     */
//    private String phone;
//
//    /**
//     * 密码，加密存储
//     */
//    private String password;
//
//    /**
//     * 昵称，默认是随机字符
//     */
//    private String nickName;
//
//    /**
//     * 用户头像
//     */
//    private String icon = "";
//
//    /**
//     * 创建时间
//     */
//    private LocalDateTime createTime;
//
//    /**
//     * 更新时间
//     */
//    private LocalDateTime updateTime;


}
