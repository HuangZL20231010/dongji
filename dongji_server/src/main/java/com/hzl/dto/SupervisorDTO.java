package com.hzl.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  huangzelin 
 * @create 2023-04-01 16:44 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorDTO {

	private ArrayList<MessageDTO> messageDTO;

	private Integer togetherDay;

	private Integer integral;

	private HashMap<String,String> me;

	private HashMap<String,String> partner;



}
