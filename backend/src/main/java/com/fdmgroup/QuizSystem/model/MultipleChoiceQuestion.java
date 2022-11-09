package com.fdmgroup.QuizSystem.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "mcquestion")
public class MultipleChoiceQuestion extends Question {

}
