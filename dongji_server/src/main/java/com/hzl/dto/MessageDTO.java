package com.hzl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {


	private String sender;

	private String title;

	private String type;

	private String posterUrl;

	private String content;




}
