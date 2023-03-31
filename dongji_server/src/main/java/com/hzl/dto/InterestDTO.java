package com.hzl.dto;

import lombok.Data;

@Data
public class InterestDTO {

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
