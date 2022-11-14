package com.fdmgroup.QuizSystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "quiz_question_mcq_attempt")
public class QuizQuestionMCQAttempt {

    @EmbeddedId
    QuizQuestionMCQAttemptKey key;

    @ManyToOne
    @MapsId("quizAttemptId")
    @JoinColumn(name = "quiz_attempt_id")
    private QuizAttempt quizAttempt;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "mcq_id")
    private MultipleChoiceQuestion multipleChoiceQuestion;


    private float awarded_grade;

    @ManyToOne
    private MultipleChoiceOption selectedOption;

}
