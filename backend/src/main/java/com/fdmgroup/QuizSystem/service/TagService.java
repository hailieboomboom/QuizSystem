package com.fdmgroup.QuizSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.repository.TagRepository;




@Service
@Transactional
public class TagService {
	
	@Autowired
	private TagRepository tagRepo;
	
	public Tag save(Tag tag) {
		return this.tagRepo.save(tag);
	}
	
	public Tag getTagByName(String name) {
		Optional<Tag> opTag = tagRepo.findByTagName(name);
		
		if(opTag.isEmpty()) {
			return null;
		}
		
		return opTag.get();
	}

}
