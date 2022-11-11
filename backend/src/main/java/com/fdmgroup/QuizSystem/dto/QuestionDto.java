package com.fdmgroup.QuizSystem.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class QuestionDto {
	
	private Long questionId;
	
	@NotBlank
	private Long userId;
	
	@NotBlank
	private String questionDetails;
	
	@NotBlank
	private List<String> tags;
	
	

}
