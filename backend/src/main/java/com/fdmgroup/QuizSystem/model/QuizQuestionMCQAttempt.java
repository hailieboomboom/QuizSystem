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
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "mcq_id")
    private MultipleChoiceQuestion multipleChoiceQuestion;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private float awarded_grade;

    @ManyToOne
    private MultipleChoiceOption multipleChoiceOption;

}
