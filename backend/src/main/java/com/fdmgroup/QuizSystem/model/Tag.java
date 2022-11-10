package com.fdmgroup.QuizSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
@Getter @Setter @NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "tags")
    @JsonIgnore
    private Set<Question> tutorials = new HashSet<>();

    @Column(name = "tag_name")
    private String tagName;
    
    public Tag(String tagName) {
        this.tagName = tagName;
    }
    
    public void addOneQuestion(Question question) {
    	if(tutorials.contains(question) == false) {
    		this.tutorials.add(question);
    	}
    }
    
    public void removeOneQuestion(Question question) {
    	if(tutorials.contains(question)) {
    		this.tutorials.remove(question);
    	}
    }

}
