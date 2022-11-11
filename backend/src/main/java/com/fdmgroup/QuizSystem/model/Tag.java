package com.fdmgroup.QuizSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tag")
@Getter @Setter @NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "tags")
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

	@Override
	public int hashCode() {
		return Objects.hash(id, tagName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		return id == other.id && Objects.equals(tagName, other.tagName);
	}
    
    

}
