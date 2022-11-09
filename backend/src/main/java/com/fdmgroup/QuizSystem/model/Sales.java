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
public class Sales extends User{
    private Role role = Role.AUTHORISED_SALES;
}