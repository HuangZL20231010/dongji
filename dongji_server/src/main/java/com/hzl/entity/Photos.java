package com.hzl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author  huangzelin 
 * @create 2023-03-30 19:21 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("photos")
public class Photos {

	/**
	 * table name:id
	 * table type:int
	 * table comment:null
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * table name:openid
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String openid;

	/**
	 * table name:photo1url
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String photo1url;

	/**
	 * table name:photo2url
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String photo2url;

	/**
	 * table name:photo3url
	 * table type:varchar(255)
	 * table comment:null
	 */
	private String photo3url;

}
