package com.fdmgroup.QuizSystem.model;
import lombok.*;
import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

}
