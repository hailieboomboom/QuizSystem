package com.fdmgroup.QuizSystem.dto;

import java.util.Set;

import com.fdmgroup.QuizSystem.model.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the QuestionGrade DTO
 * @author sm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * This class contains question info, for example: grade, tags, questionDetails.
 * This dto will be sent to the user.
 */
public class QuestionGradeDTO {

    private long questionId;
	private String questionDetails;
    private float grade;
    private Set<Tag> tags;

}
