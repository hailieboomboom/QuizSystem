package com.fdmgroup.QuizSystem.model;

import lombok.*;
import javax.persistence.*;

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
	
	

}
