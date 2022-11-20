package com.fdmgroup.QuizSystem.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Trainer entity. Default role is UNAUTHORISED_TRAINER.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Trainer extends User{

    private Role role = Role.UNAUTHORISED_TRAINER;

}
