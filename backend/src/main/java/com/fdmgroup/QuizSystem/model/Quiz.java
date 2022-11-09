package com.fdmgroup.QuizSystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	private long id;

	private QuizCategory quizCategory;

	@ManyToMany
	private List<Question> questions;


	public Quiz(QuizCategory quizCategory, List<Question> questions) {
		super();
		this.quizCategory = quizCategory;
		this.questions = questions; // Constructor of owning side include inverse side
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", quizCategory=" + quizCategory + "]";
	}

}

//@ManyToMany
//@JoinTable(name="quiz_question", 
//	    joinColumns=@JoinColumn(name="quiz_id"),
//	    inverseJoinColumns= @JoinColumn(name="question_id"))
//private List<Question> questions;


//@ManyToOne
//@Column(name="created_by_id")
//private User user;



// TODO: Need to implement following to other classes
//// User.java
//@OneToMany(mappedBy="user")
//private List<Quiz> quizzes;
//
//// Question.java
//@ManyToMany(mappedBy="questions")
//private List<Quiz> quizzes;
