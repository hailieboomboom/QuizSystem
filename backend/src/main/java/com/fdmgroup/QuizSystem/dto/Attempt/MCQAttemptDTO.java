package com.fdmgroup.QuizSystem.dto.Attempt;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MCQAttemptDTO {
	private long quizAttemptId;
	@NotBlank
	private long mcqId;
    private float awarded_grade;
	@NotBlank
    private long selectedOption;
}
