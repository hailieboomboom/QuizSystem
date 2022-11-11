package com.fdmgroup.QuizSystem.model;

import lombok.*;

import java.util.Objects;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "saquestion")
public class ShortAnswerQuestion extends Question{
	
	private String correctAnswer;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(correctAnswer);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShortAnswerQuestion other = (ShortAnswerQuestion) obj;
		return Objects.equals(correctAnswer, other.correctAnswer);
	}
	
	

}
