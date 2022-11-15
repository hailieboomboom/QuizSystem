package com.fdmgroup.QuizSystem.util;

import com.fdmgroup.QuizSystem.dto.QuestionGradeDTO;
import com.fdmgroup.QuizSystem.dto.QuizDto;
import com.fdmgroup.QuizSystem.dto.RoleDTO;
import com.fdmgroup.QuizSystem.dto.UserOutputDTO;
import com.fdmgroup.QuizSystem.dto.Attempt.MCQAttemptDTO;
import com.fdmgroup.QuizSystem.dto.Attempt.QuizAttemptDTO;
import com.fdmgroup.QuizSystem.model.*;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;
import com.fdmgroup.QuizSystem.service.QuizQuestionMCQAttemptService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelToDTO {

    private ModelMapper modelMapper;
    
//    @Autowired
//    private QuizQuestionMCQAttemptService mcqAttemptService;

    @Autowired
    public ModelToDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.typeMap(Quiz.class, QuizDto.class)
                .addMapping(Quiz::getQuizCategory, QuizDto::setQuizCategory)
                .addMapping(Quiz::getName, QuizDto::setName)
                .addMapping(Quiz::getId, QuizDto::setQuizId)
                .addMapping(quiz -> quiz.getCreator().getId(), QuizDto::setCreatorId);

        modelMapper.typeMap(User.class, UserOutputDTO.class)
                .addMapping(User::getRole, UserOutputDTO::setRole);

        modelMapper.typeMap(QuizQuestionGrade.class, QuestionGradeDTO.class)
                .addMapping(quizQuestionGrade -> quizQuestionGrade.getQuestion().getId(), QuestionGradeDTO::setQuestionId)
                .addMapping(QuizQuestionGrade::getGrade, QuestionGradeDTO::setGrade);
        
        modelMapper.typeMap(QuizAttempt.class, QuizAttemptDTO.class)
                .addMapping(QuizAttempt::getId, QuizAttemptDTO::setId)
                .addMapping(qa -> qa.getQuiz().getId(), QuizAttemptDTO::setQuizId)
                .addMapping(qa -> qa.getUser().getId(), QuizAttemptDTO::setUserId)
                .addMapping(QuizAttempt::getAttemptNo, QuizAttemptDTO::setAttemptNo)
                .addMapping(QuizAttempt::getTotalAwarded, QuizAttemptDTO::setTotalAwarded);
//                .addMapping(qa -> qa.getAttemptedMCQs().stream().map(mcq -> mcqAttemptToOutput(mcq)), QuizAttemptDTO::setMCQAttemptList);

        modelMapper.typeMap(QuizQuestionMCQAttempt.class, MCQAttemptDTO.class)
                .addMapping(mcqAttempt -> mcqAttempt.getQuizAttempt().getId(), MCQAttemptDTO::setQuizAttemptId)
                .addMapping(mcqAttempt -> mcqAttempt.getMultipleChoiceQuestion().getId(), MCQAttemptDTO::setMcqId)
                .addMapping(QuizQuestionMCQAttempt::getAwarded_grade, MCQAttemptDTO::setAwarded_grade)
                .addMapping(mcqAttempt -> mcqAttempt.getSelectedOption().getId(), MCQAttemptDTO::setSelectedOption);

    }

    public UserOutputDTO userToOutput(User user){
        return modelMapper.map(user, UserOutputDTO.class);
    }

    public QuizDto quizToOutput(Quiz quiz) {
        return modelMapper.map(quiz, QuizDto.class);
    }

    public QuestionGradeDTO qqgToQg(QuizQuestionGrade quizQuestionGrade){
        return modelMapper.map(quizQuestionGrade, QuestionGradeDTO.class);
    }

    public QuizAttemptDTO quizAttemptToOutput(QuizAttempt quizAttempt){
        return modelMapper.map(quizAttempt, QuizAttemptDTO.class);
    }

    public MCQAttemptDTO mcqAttemptToOutput(QuizQuestionMCQAttempt mcqAttempt){
        return modelMapper.map(mcqAttempt, MCQAttemptDTO.class);
    }


}
