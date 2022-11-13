package com.fdmgroup.QuizSystem.dto;
import com.fdmgroup.QuizSystem.model.User;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotBlank;

@Data
public class SAQDto {
	
	private Long saqId;
	
	@NotBlank
	private Long userId;
	
	@NotBlank
	private String questionDetails;
	
	@NotBlank
	private String correctAnswer;
	
	@NotBlank
	private List<String> tags;
	

}
