package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor @Getter @Setter
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
