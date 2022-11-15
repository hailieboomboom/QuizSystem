package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class McqOptionDto {
	private Long id;
    @NotBlank
    private String optionDescription;
    @NotBlank
    private boolean isCorrect;
}
