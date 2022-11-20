package com.fdmgroup.QuizSystem.model;

import lombok.*;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * This class inherited from question class.
 * one multiple choice question can have multiple options
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "mcquestion")
public class MultipleChoiceQuestion extends Question {
	
	@OneToMany(mappedBy="mcq")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<MultipleChoiceOption> mcoptions;
	

}
