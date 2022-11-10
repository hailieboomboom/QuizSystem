package com.fdmgroup.QuizSystem.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

//	@ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "quiz_question", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
	private List<Question> questions;

	@ManyToOne
	private User creator;

	
	public Quiz(String name, QuizCategory quizCategory, List<Question> questions, User creator) {
		super();
		this.name = name;
		this.quizCategory = quizCategory;
		this.questions = questions;
		this.creator = creator;
	}
	
	public void addQuestion(Question question) {
		this.questions.add(question);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
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
