package com.fdmgroup.QuizSystem.dto.Attempt;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Data Transfer Object for QuizAttempt.
 * @author Yutta
 *
 */
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
    
	private String quizName;
	private String quizTakerName;
}
