package com.fdmgroup.QuizSystem.dto.Attempt;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Data Transfer Object for QuizQuestionMCQAttempt.
 * @author Yutta
 *
 */
@Data
public class MCQAttemptDTO {
	private long quizAttemptId;
	@NotBlank
	private long mcqId;
    private float awarded_grade;
	@NotBlank
    private long selectedOption;
}
