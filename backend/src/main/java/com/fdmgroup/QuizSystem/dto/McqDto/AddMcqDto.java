package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * This class is an object that contains Mcq info including: options, tags, question details and ids.
 * The object will be sent to frontend when user request mcq
 */
@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public class AddMcqDto {

    @NotBlank
    private String questionDetails;
    @NotBlank
    private long userId;
    @NotBlank
    private List<McqOptionDto> Options;
    @NotBlank
    private List<String>  tags;
    
    

}
