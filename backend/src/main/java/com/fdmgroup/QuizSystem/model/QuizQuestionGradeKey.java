package com.fdmgroup.QuizSystem.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import lombok.*;

@SuppressWarnings("serial")
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class QuizQuestionGradeKey implements Serializable{
	
	@Column(name = "quiz_id")
	Long quizId;
	
	@Column(name = "question_id")
	Long questionId;
	
	

	@Override
	public int hashCode() {
		return Objects.hash(quizId, questionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizQuestionGradeKey other = (QuizQuestionGradeKey) obj;
		return Objects.equals(quizId, other.quizId) && Objects.equals(questionId, other.questionId);
	}

	public QuizQuestionGradeKey(Long quizId, Long questionId) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
	}
	
	

}
