package com.fdmgroup.QuizSystem.model;
import lombok.*;
import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column( unique = true, length = 45, nullable = false)
    private String username;

    @Column( nullable = false)
    private String password;

    @Column( unique = true, length = 45, nullable = false)
    private String email;

    private String firstname;

    private String lastname;

    private Role role;

}
