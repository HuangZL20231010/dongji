package com.hzl.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author  huangzelin 
 * @create 2023-03-31 14:49 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {

	private Integer id;

	private String startTime;


	private String endTime;


	private String sport;


	private String target;

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date date;


}
