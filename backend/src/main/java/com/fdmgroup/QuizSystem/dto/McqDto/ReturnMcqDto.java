package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ReturnMcqDto {
    private long questionId;
    private String questionDetail;
    private List<String> tags;
    private List<McqOptionDto> mcqOptionDtoList;
}
