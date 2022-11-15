package com.fdmgroup.QuizSystem.dto;

import lombok.Data;

@Data
public class QuestionGradeDTO {

    private long questionId;
	private String questionDetails;
    private float grade;
    

}
