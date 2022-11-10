package com.fdmgroup.QuizSystem.dto;

import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class QuizDto {
	private QuizCategory quizCategory;
	private List<Question> questions;
	private long creatorId;
}
