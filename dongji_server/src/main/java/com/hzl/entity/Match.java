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
 * @create 2023-03-31 17:35 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`match`")
public class Match {

	/**
	 * table name:id
	 * table type:int
	 * table comment:null
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * table name:inviterId
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String inviterId;

	/**
	 * table name:inviteeId
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String inviteeId;

	/**
	 * table name:isComplete
	 * table type:int
	 * table comment:0:未完成  1:完成
	 */
	private Integer isComplete;

	/**
	 * table name:sendDate
	 * table type:timestamp
	 * table comment:null
	 */
	private Timestamp sendDate;

	/**
	 * table name:status
	 * table type:int
	 * table comment:0:d等待  1:成功  2:失败
	 */
	private Integer status;

}
