package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author  huangzelin 
 * @create 2023-04-01 08:49 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`group`")
public class Group {

	/**
	 * table name:groupId
	 * table type:int
	 * table comment:null
	 */
	@TableId(type = IdType.AUTO)
	private Integer groupId;

	/**
	 * table name:beginDay
	 * table type:timestamp
	 * table comment:null
	 */
	private Timestamp beginDay;

	/**
	 * table name:posterNum
	 * table type:int
	 * table comment:null
	 */
	private Integer posterNum;

	/**
	 * table name:integral
	 * table type:int
	 * table comment:null
	 */
	private Integer integral;

}
