package com.fdmgroup.QuizSystem.util;

import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.QuizResponse;
import com.fdmgroup.QuizSystem.dto.UserOutputDTO;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelToDTO {

    private ModelMapper modelMapper;

    @Autowired
    public ModelToDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.typeMap(Quiz.class, QuizResponse.class)
                .addMapping(Quiz::getQuizCategory, QuizResponse::setQuizCategory)
                .addMapping(Quiz::getName, QuizResponse::setName)
                .addMapping(Quiz::getId, QuizResponse::setQuizId)
                .addMapping(quiz -> quiz.getCreator().getId(), QuizResponse::setCreatorId);

        modelMapper.typeMap(User.class, UserOutputDTO.class)
                .addMapping(User::getRole, UserOutputDTO::setRole);

        modelMapper.typeMap(QuizQuestionGrade.class, QuestionGradeDTO.class)
                .addMapping(quizQuestionGrade -> quizQuestionGrade.getQuestion().getId(), QuestionGradeDTO::setQuestionId)
                .addMapping(QuizQuestionGrade::getGrade, QuestionGradeDTO::setGrade);
    }

    public UserOutputDTO userToOutput(User user){
        return modelMapper.map(user, UserOutputDTO.class);
    }

    public QuizResponse quizToOutput(Quiz quiz) {
        return modelMapper.map(quiz, QuizResponse.class);
    }

    public QuestionGradeDTO qqgToQg(QuizQuestionGrade quizQuestionGrade){
        return modelMapper.map(quizQuestionGrade, QuestionGradeDTO.class);
    }

}
