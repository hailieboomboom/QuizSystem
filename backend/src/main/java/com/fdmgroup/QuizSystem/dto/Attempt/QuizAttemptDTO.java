package com.fdmgroup.QuizSystem.dto.Attempt;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class QuizAttemptDTO {
	private long id;
	@NotBlank
	private long quizId;
	@NotBlank
	private long userId;
	private int attemptNo;
    private float totalAwarded;
    List<MCQAttemptDTO> MCQAttemptList;
}
