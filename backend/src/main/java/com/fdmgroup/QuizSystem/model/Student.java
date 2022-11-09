package com.fdmgroup.QuizSystem.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student extends User{

    private Role role = Role.TRAINING;
}
