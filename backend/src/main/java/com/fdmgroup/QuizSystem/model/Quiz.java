package com.fdmgroup.QuizSystem.model;

import java.util.List;

import javax.persistence.*;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private QuizCategory quizCategory;


//	@ManyToMany
//	@JoinTable(name="QUIZ_QUESTION", 
//		    joinColumns=@JoinColumn(name="quiz_id"),
//		    inverseJoinColumns= @JoinColumn(name="question_id"))
//	private List<Question> questions;
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<QuizQuestionGrade> quizQuestionsGrade;
	
	@ManyToOne
	private User creator;

	public Quiz(String name, QuizCategory quizCategory, List<QuizQuestionGrade> quizQuestionsGrade, User creator) {
		super();
		this.name = name;
		this.quizCategory = quizCategory;
		this.quizQuestionsGrade = quizQuestionsGrade;
		this.creator = creator;
	}
    

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", quizCategory=" + quizCategory + "]";

	}

	
}




// TODO: Need to implement following to other classes
//// User.java
//@OneToMany(mappedBy="creator", cascade=CascadeType.ALL)
//private List<Quiz> createdQuizzes;
//
//// Question.java
//@ManyToMany(mappedBy="questions", fetch = FetchType.EAGER?)
//private List<Quiz> quizzes;
