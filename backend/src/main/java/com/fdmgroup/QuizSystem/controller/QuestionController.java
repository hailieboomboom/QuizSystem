package com.fdmgroup.QuizSystem.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.common.ApiResponse;
import com.fdmgroup.QuizSystem.dto.SAQDto;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.ShortAnswerQuestionService;
import com.fdmgroup.QuizSystem.service.TagService;
import com.fdmgroup.QuizSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions") // http://localhost:8088/QuestionSystem/questions
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private TagService tagService;
	
	@Autowired
	private ShortAnswerQuestionService saqService;
	
	@PostMapping("/saqs")
	public ResponseEntity<ApiResponse> postShortAnswerQuestion(@RequestBody SAQDto saqDto) {
		
		ShortAnswerQuestion saq = new ShortAnswerQuestion();
		User creator = userService.getUserById(saqDto.getUserId());
		saq.setCreator(creator);
		saq.setCorrectAnswer(saqDto.getCorrectAnswer());
		saq.setQuestionDetails(saqDto.getQuestionDetails());
		
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			saq.addOneTag(t);
		}
		
		questionService.save(saq);
		
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			t.addOneQuestion(saq);
			tagService.save(t);
		}
		
		
		return new ResponseEntity<>(new ApiResponse(true, "create short answer question success"), HttpStatus.CREATED);
	}
	
	
	// get all questions
	@GetMapping
	public List<Question> getAllQuestion(){
		return questionService.findAllQuestions();
	}
	
	// get one saq  by id
	@GetMapping("/saqs/{id}")
	public SAQDto getOneShortAnswerQuestion(@PathVariable Long id) {
		
		ShortAnswerQuestion saq = saqService.findById(id);
		SAQDto saqDto = new SAQDto();
		saqDto.setUserId(saq.getCreator().getId());
		saqDto.setCorrectAnswer(saq.getCorrectAnswer());
		saqDto.setQuestionDetails(saq.getQuestionDetails());
		saqDto.setSaqId(id);
		List<String> tagnames = new ArrayList<String>();
		for(Tag t: saq.getTags()) {
			tagnames.add(t.getTagName());
		}
		saqDto.setTags(tagnames);
		return saqDto;
	}
	
	// get all saqs
	@GetMapping("/saqs")
	public List<SAQDto> getAllShortAnswerQuestion(){
		List<ShortAnswerQuestion> saqs = saqService.findAll();
		List<SAQDto> saqDtos = new ArrayList<SAQDto>();
		
		for(ShortAnswerQuestion saq: saqs) {
			SAQDto saqDto = new SAQDto();
			saqDto.setUserId(saq.getCreator().getId());
			saqDto.setCorrectAnswer(saq.getCorrectAnswer());
			saqDto.setQuestionDetails(saq.getQuestionDetails());
			saqDto.setSaqId(saq.getId());
			List<String> tagnames = new ArrayList<String>();
			for(Tag t: saq.getTags()) {
				tagnames.add(t.getTagName());
			}
			saqDto.setTags(tagnames);
			saqDtos.add(saqDto);
		}
		return saqDtos;
	}
	
	// update saq
	@PutMapping("/saqs/{id}")
	public ResponseEntity<ApiResponse> updateShortAnswerQuestion(@RequestBody SAQDto saqDto, @PathVariable Long id){
		
		ShortAnswerQuestion saq = saqService.findById(id);
		saq.setCorrectAnswer(saqDto.getCorrectAnswer());
		saq.setQuestionDetails(saqDto.getQuestionDetails());
		
		// remove question from tags
		Set<Tag> oldtags = saq.getTags();
		for(Tag t: oldtags) {
			t.removeOneQuestion(saq);
			tagService.save(t);
		}
		// update tags by adding new tags
		saq.setTags(new HashSet<Tag>());
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			saq.addOneTag(t);
		}
		
        questionService.save(saq);
		
		for(String tagName: saqDto.getTags()) {
			Tag t = tagService.getTagByName(tagName);
			t.addOneQuestion(saq);
			tagService.save(t);
		}
		
		return new ResponseEntity<>(new ApiResponse(true, "update short answer question success"), HttpStatus.OK);
		
	}
	
	// delete saq
	@DeleteMapping("/saqs/{id}")
	public ResponseEntity<ApiResponse> deleteOneShortAnswerQuestion(@PathVariable Long id){
		
		ShortAnswerQuestion saq = saqService.findById(id);
		
		// remove question from tags
		Set<Tag> oldtags = saq.getTags();
		for(Tag t: oldtags) {
			t.removeOneQuestion(saq);
			tagService.save(t);
		}
		
		// delete saq
		questionService.remove(saq);
		
		return new ResponseEntity<>(new ApiResponse(true, "delete short answer question success"), HttpStatus.OK);
		
	}
	
	// view all questions (just view question body)
	

}
