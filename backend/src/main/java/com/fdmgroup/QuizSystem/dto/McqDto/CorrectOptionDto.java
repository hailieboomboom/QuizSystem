package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CorrectOptionDto {
    private long optionId;
    private String optionDescription;
    private boolean isCorrect;
}
