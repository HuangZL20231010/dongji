package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author  huangzelin 
 * @create 2023-03-31 14:49 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

	/**
	 * table name:id
	 * table type:int
	 * table comment:null
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * table name:openid
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String openid;

	/**
	 * table name:planDate
	 * table type:datetime
	 * table comment:null
	 */
	private String planDate;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@TableField(exist = false)
	private Date date;

	/**
	 * table name:startTime
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String startTime;

	/**
	 * table name:endTime
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String endTime;

	/**
	 * table name:sport
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String sport;

	/**
	 * table name:target
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String target;

	/**
	 * table name:is_finish
	 * table type:int
	 * table comment:1：完成 0：未完成
	 */
	private Integer isFinish;

}
