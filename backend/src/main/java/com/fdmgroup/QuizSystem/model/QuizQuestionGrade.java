package com.fdmgroup.QuizSystem.model;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "quiz_question_grade")
public class QuizQuestionGrade {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private Long id;
	
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
