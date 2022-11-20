package com.fdmgroup.QuizSystem.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;


/**
 * This class is an object that contains Question info including: tags, question details and ids.
 * The object will be sent to frontend when user requests question.
 */
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
