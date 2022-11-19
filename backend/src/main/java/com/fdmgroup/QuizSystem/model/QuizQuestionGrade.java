package com.fdmgroup.QuizSystem.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * QuizQuestionGrade entity which mainly represents the questions and the grades of the questions of a quiz
 * @author Hailie
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "quiz_question_grade")
public class QuizQuestionGrade {
		
	@EmbeddedId
	QuizQuestionGradeKey key;
	
	@ManyToOne
	@MapsId("quizId")
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
	
	@ManyToOne
	@MapsId("questionId")
	@JoinColumn(name = "question_id")
	private Question question;
	
	private float grade;

}
