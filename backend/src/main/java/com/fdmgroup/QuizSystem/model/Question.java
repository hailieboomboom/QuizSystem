package com.fdmgroup.QuizSystem.model;

import java.util.HashSet;
import java.util.List;

import java.util.Objects;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    

//    @ManyToMany(mappedBy="questions")
//    private List<Quiz> quizzes;

	// To Haile, Jason and Yutta think we don't need this part of code because it already exists in the quiz model.
//    @OneToMany(mappedBy = "question")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	private List<QuizQuestionGrade> quizQuestionsGrade;
    
    @ManyToMany(fetch = FetchType.EAGER)
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

	@Override
	public int hashCode() {
		return Objects.hash(id, questionDetails);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return id == other.id && Objects.equals(questionDetails, other.questionDetails);
	}
	
	

    
    
}
