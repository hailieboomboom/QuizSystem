package com.fdmgroup.QuizSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Composite Key for QuizQuestionMCQAttempt
 * @author Yutta
 *
 */

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestionMCQAttemptKey implements Serializable {

    @Column(name = "quiz_attempt_id")
    Long quizAttemptId;

    @Column(name = "question_id")
    Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizQuestionMCQAttemptKey that = (QuizQuestionMCQAttemptKey) o;
        return quizAttemptId.equals(that.quizAttemptId) && questionId.equals(that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizAttemptId, questionId);
    }
}
