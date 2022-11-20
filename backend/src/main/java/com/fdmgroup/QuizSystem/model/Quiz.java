package com.fdmgroup.QuizSystem.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Quiz entity which contains details of the quiz.
 * @author sm
 *
 */
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