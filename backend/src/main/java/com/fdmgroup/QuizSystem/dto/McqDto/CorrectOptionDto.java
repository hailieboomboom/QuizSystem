package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class contains correct option info
 * This object will be sent to the frontend, when user request correct option of a question
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CorrectOptionDto {
    private long optionId;
    private String optionDescription;
    private boolean isCorrect;
}
