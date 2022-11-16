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
	private long userId; // AKA: quiz taker's ID, quiz taker = quiz attempt creator
	private int attemptNo;
    private float totalAwarded;
    private float maxGrade;
    List<MCQAttemptDTO> MCQAttemptList;
}
