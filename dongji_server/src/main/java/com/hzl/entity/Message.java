package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author  huangzelin 
 * @create 2023-04-01 16:44 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message {

	/**
	 * table name:id
	 * table type:int
	 * table comment:null
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * table name:sendUserId
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String sendUserId;

	/**
	 * table name:groupId
	 * table type:int
	 * table comment:null
	 */
	private Integer groupId;

	/**
	 * table name:title
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String title;

	/**
	 * table name:type
	 * table type:varchar(12)
	 * table comment:poster  or   text
	 */
	private String type;

	/**
	 * table name:posterUrl
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String posterUrl;

	/**
	 * table name:content
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String content;

}
