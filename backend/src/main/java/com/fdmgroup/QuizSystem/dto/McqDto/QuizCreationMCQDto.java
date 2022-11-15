package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class QuizCreationMCQDto {
	private Long mcqId;
	private String questionDetails;
	private float grade;

}
