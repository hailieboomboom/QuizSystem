package com.fdmgroup.QuizSystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	private QuizCategory quizCategory;

	@ManyToMany
	@JoinTable(name="QUIZ_QUESTION", 
		    joinColumns=@JoinColumn(name="quiz_id"),
		    inverseJoinColumns= @JoinColumn(name="question_id"))
	private List<Question> questions;
	
	@ManyToOne
	private User creator;


	public Quiz(QuizCategory quizCategory, List<Question> questions) {
		super();
		this.quizCategory = quizCategory;
		this.questions = questions; // Constructor of owning side include inverse side
//		this.creator = creator; 
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", quizCategory=" + quizCategory + "]";
	}

}



// TODO: Need to implement following to other classes
//// User.java
//@OneToMany(mappedBy="user")
//private List<Quiz> quizzes;
//
//// Question.java
//@ManyToMany(mappedBy="questions")
//private List<Quiz> quizzes;
