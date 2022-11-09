package com.fdmgroup.QuizSystem.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UnauthorisedTrainer extends User {
    private Role role = Role.UNAUTHORISED_TRAINER;
}
