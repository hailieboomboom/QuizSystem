package com.fdmgroup.QuizSystem.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UnauthorisedSales extends User{
    private Role role = Role.UNAUTHORISED_SALES;
}
