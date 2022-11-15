package com.fdmgroup.QuizSystem.dto;

import com.fdmgroup.QuizSystem.model.QuizCategory;

import lombok.Data;

@Data
public class QuizDto {
	
	private long quizId; 
	private long creatorId;
	private String name;
	private QuizCategory quizCategory;	

}
