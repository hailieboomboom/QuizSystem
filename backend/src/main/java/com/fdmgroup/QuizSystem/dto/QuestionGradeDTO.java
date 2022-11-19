package com.fdmgroup.QuizSystem.dto;

import java.util.Set;

import com.fdmgroup.QuizSystem.model.Tag;

import lombok.Data;

/**
 * Represents the QuestionGrade DTO
 * @author sm
 *
 */
@Data
public class QuestionGradeDTO {

    private long questionId;
	private String questionDetails;
    private float grade;
    private Set<Tag> tags;

}
