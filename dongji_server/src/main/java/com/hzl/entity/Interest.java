package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author  azha 
 * @create 2023-03-30 11:06 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("interest")
@Component
public class Interest {

	/**
	 * table name:id
	 * table type:int
	 * table comment:null
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * table name:openid
	 * table type:int
	 * table comment:null
	 */
	private String openid;

	/**
	 * table name:running
	 * table type:tinyint
	 * table comment:0:未选中  1选中
	 */
	private Long running;

	/**
	 * table name:skipping
	 * table type:tinyint
	 * table comment:null
	 */
	private Long skipping;

	/**
	 * table name:football
	 * table type:tinyint
	 * table comment:null
	 */
	private Long football;

	/**
	 * table name:basketball
	 * table type:tinyint
	 * table comment:null
	 */
	private Long basketball;

	/**
	 * table name:tennis
	 * table type:tinyint
	 * table comment:null
	 */
	private Long tennis;

	/**
	 * table name:table_tennis
	 * table type:tinyint
	 * table comment:null
	 */
	@TableField(value = "table_tennis")
	private Long tableTennis;

	/**
	 * table name:yoga
	 * table type:tinyint
	 * table comment:null
	 */
	private Long yoga;

	/**
	 * table name:bodybuilding
	 * table type:tinyint
	 * table comment:null
	 */
	private Long bodybuilding;

	/**
	 * table name:swimming
	 * table type:tinyint
	 * table comment:null
	 */
	private Long swimming;

}
