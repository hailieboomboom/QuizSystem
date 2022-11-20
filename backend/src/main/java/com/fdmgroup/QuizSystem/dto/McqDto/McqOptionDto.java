package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * This class contains information about option , for example: id, description, correct or not.
 * This object will mainly be used to send option info to user / or store option info received from user.
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class McqOptionDto {
	private Long id;
    @NotBlank
    private String optionDescription;
    @NotBlank
    private boolean isCorrect;
	@Override
	public String toString() {
		return "McqOptionDto [id=" + id + ", optionDescription=" + optionDescription + ", isCorrect=" + isCorrect + "]";
	}
    
    
}
