package com.fdmgroup.QuizSystem.model;

import lombok.*;
import javax.persistence.*;

/**
 * This class defines mcqOption entity attributes, including:id, detail and the option is correct or not
 * One question can have multiple options
 * */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "mcoption")
public class MultipleChoiceOption {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
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
