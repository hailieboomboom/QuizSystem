package com.fdmgroup.QuizSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionMCQAttemptKey implements Serializable {

    @Column(name = "quiz_id")
    Long quizId;

    @Column(name = "question_id")
    Long questionId;

    @Column(name = "user_id")
    Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizQuestionMCQAttemptKey that = (QuizQuestionMCQAttemptKey) o;
        return quizId.equals(that.quizId) && questionId.equals(that.questionId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, questionId, userId);
    }
}
