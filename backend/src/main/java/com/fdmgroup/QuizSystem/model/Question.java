package com.fdmgroup.QuizSystem.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@Table( name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String questionDetails;
    
    @ManyToOne
    private User creator;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "QUESTION_Tag",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<Tag> tags = new HashSet<>();

	public Question(String questionDetails, User creator) {
		super();
		this.questionDetails = questionDetails;
		this.creator = creator;
	}
    
    public void addOneTag(Tag tag) {
    	if(tags.contains(tag) == false) {
    		this.tags.add(tag);
    	}
    	
    }

    
}
