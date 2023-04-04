package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author  huangzelin 
 * @create 2023-04-01 08:32 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partner {

	/**
	 * table name:openid
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String openid;

	/**
	 * table name:id
	 * table type:int
	 * table comment:null
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * table name:partnerId
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String partnerId;

	/**
	 * table name:groupId
	 * table type:int
	 * table comment:null
	 */
	private Integer groupId;

}
