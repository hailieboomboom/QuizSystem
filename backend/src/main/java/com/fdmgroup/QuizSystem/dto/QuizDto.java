package com.fdmgroup.QuizSystem.dto;

import com.fdmgroup.QuizSystem.model.QuizCategory;

import lombok.Data;

/**
 * Represents the Quiz DTO.
 * @author sm
 *
 */
@Data
public class QuizDto {
	
	private long quizId; 
	private long creatorId;
	private String name;
	private QuizCategory quizCategory;	

}
