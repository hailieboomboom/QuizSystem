package com.fdmgroup.QuizSystem.dto.McqDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This class is an object that contains Mcq info including: options, tags, question details and ids.
 * The object will be used to store mcq info received from user
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReturnMcqDto {
    private long questionId;
    private String questionDetail;
    private List<String> tags;
    private List<McqOptionDto> mcqOptionDtoList;
}
