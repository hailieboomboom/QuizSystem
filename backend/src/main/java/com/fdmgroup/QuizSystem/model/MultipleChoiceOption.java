package com.fdmgroup.QuizSystem.model;

import lombok.*;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "mcoption")
public class MultipleChoiceOption {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	private String optionDetail;
	private boolean isCorrect;
	
	@ManyToOne
	private MultipleChoiceQuestion mcq;

	public MultipleChoiceOption(String optionDetail, boolean isCorrect, MultipleChoiceQuestion mcq) {
		super();
		this.optionDetail = optionDetail;
		this.isCorrect = isCorrect;
		this.mcq = mcq;
	}
	
	

}
