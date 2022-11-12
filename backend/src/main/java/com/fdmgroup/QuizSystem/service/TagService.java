package com.fdmgroup.QuizSystem.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.exception.McqException.TagNotValidException;
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
	
	public TagService(TagRepository tagRepo) {
		super();
		this.tagRepo = tagRepo;
	}

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


	public Set<Tag> getTagsFromDto(List<String> tagDto) {
		Set<Tag> tagSet = new HashSet<>();
		if(tagDto.size()<1)
			throw new TagNotValidException("Please select at least one tag");
		if(!tagDto.contains("course")&&!tagDto.contains("interview"))
			throw new TagNotValidException("The question must contains at least a course or interview tag");

		tagDto.forEach(tagName -> tagSet.add(validateTag(tagName)));
		return tagSet;
	}

	public Tag validateTag(String tagName) {

		Optional<Tag> tagOptional = tagRepo.findByTagName(tagName.toLowerCase());
		if (tagOptional.isEmpty())
			throw new NoDataFoundException( tagName + " doesn't exists, please select another one or create a tag first");
		Tag tag = tagOptional.get();
		return tag;
	}



}
