package com.fdmgroup.QuizSystem.service;

import java.util.ArrayList;
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


/**
 * This class handles all CRUD logic for tags
 */
@Service
@Transactional
public class TagService {

	public static final String TAG_NOT_EXISTS = " doesn't exists, please select another one or create a tag first";
	public static final String INTERVIEW_TAG = "interview";
	public static final String COURSE_TAG = "course";
	public static final String NO_TAG_PROVIDED = "Please select at least one tag";
	public static final String MISSING_COURSE_OR_INTERVIEW_TAG = "The question must contains at least a course or interview tag";
	@Autowired
	private final TagRepository tagRepo;
	
	public TagService(TagRepository tagRepo) {
		super();
		this.tagRepo = tagRepo;
	}

	/**
	 * save tag to database
	 * @param tag the tag that will be saved in database
	 * @return managed tag object
	 */
	public Tag save(Tag tag) {
		return this.tagRepo.save(tag);
	}

	/**
	 * find a tag object by tag name
	 * @param name the name of a tag
	 * @return tag object
	 */
	public Tag getTagByName(String name) {
		Optional<Tag> opTag = tagRepo.findByTagName(name);
		
		if(opTag.isEmpty()) {
			return null;
		}
		
		return opTag.get();
	}

	/**
	 * transfer a list of tagDto to a list of tag
	 * @param tagDto a list of tagDto
	 * @return a set of tags object
	 */
	public Set<Tag> getTagsFromDto(List<String> tagDto) {
		Set<Tag> tagSet = new HashSet<>();
		tagDto.forEach(tagName -> tagSet.add(validateTagName(tagName)));
		return tagSet;
	}

	/**
	 * make sure user can only use tag that is already in the database
	 * @param tagName the name of a tag
	 * @return throw an error if tag doesn't exist/ return a tag object if exists
	 */
	public Tag validateTagName(String tagName) {

		Optional<Tag> tagOptional = tagRepo.findByTagName(tagName.toLowerCase());
		if (tagOptional.isEmpty())
			throw new NoDataFoundException( tagName + TAG_NOT_EXISTS);
		return tagOptional.get();
	}

	/**
	 * make sure the question contains at least a course/interview tag.
	 * @param tagDto the tagDto is from frontend
	 */
	public void validateTagsFromDto(List<String> tagDto) {
		if(tagDto.size()<1)
			throw new TagNotValidException(NO_TAG_PROVIDED);
		if(!tagDto.contains(COURSE_TAG)&&!tagDto.contains(INTERVIEW_TAG))
			throw new TagNotValidException(MISSING_COURSE_OR_INTERVIEW_TAG);

		tagDto.forEach(tagName -> validateTagName(tagName));
	}

	/**
	 * find all tags in the database
	 * @return a list of tag names
	 */
	public List<String> findAll() {
		List<Tag> tags = tagRepo.findAll();
		List<String> tagNames = new ArrayList<>();
		for(Tag tag: tags) {
			tagNames.add(tag.getTagName());
		}
		return tagNames;
	}

	/**
	 * get a tag by tag id
	 * @param id tag Id
	 * @return the name of the tag that found in database
	 */
	public String getTagById(long id) {
		Optional<Tag> tagOptional = tagRepo.findById(id);
		if(tagOptional.isEmpty()) {
			return "";
		}
		return tagOptional.get().getTagName();
	}



}
