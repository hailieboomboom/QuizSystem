package com.fdmgroup.QuizSystem.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "saquestion")
public class ShortAnswerQuestion extends Question{
	
	private String correctAnswer;

}
