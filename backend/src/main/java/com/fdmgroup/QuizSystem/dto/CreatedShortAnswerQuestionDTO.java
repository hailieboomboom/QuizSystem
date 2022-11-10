package com.fdmgroup.QuizSystem.dto;
import com.fdmgroup.QuizSystem.model.User;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CreatedShortAnswerQuestionDTO {
	
	private Long id;
	
	private String questionDetails;
	
	private String correctAnswer;
	

}
