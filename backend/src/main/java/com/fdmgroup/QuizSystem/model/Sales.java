package com.fdmgroup.QuizSystem.model;
import lombok.*;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Sales entity. Default role is UNAUTHORISED_SALES.
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
public class Sales extends User{
    private Role role = Role.UNAUTHORISED_SALES;
}
