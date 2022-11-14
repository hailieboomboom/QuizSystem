package com.fdmgroup.QuizSystem.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quiz_attempt")
public class QuizAttempt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
	private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private int attemptNo;
    
    private float totalAwarded;
    
}
