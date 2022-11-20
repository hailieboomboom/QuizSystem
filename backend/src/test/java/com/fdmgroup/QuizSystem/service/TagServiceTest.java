package com.fdmgroup.QuizSystem.service;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;


import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.exception.McqException.TagNotValidException;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.repository.TagRepository;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
	
	private TagService tagService;
	private Tag tag;
	private Optional<Tag> opTag;
	
	@Mock
	TagRepository mockTagRepo;
	
	@BeforeEach
	void setup() {
		tagService = new TagService(mockTagRepo);
		tag = new Tag();
	}

	@Test
	void test_save_tag_will_call_save_from_tag_repo() {
		tagService.save(tag);
		
		verify(mockTagRepo, times(1)).save(tag);
	}
	
	@Test
	void test_gettagbyname_return_null_if_no_tag_found() {
		String name = "test";
		opTag = Optional.empty();
		when(mockTagRepo.findByTagName(name)).thenReturn(opTag);
		
		Tag result = tagService.getTagByName(name);
		
		verify(mockTagRepo, times(1)).findByTagName(name);
		assertNull(result);
	}
	
	@Test
	void test_gettagbyname_return_tag_with_name_if_a_tag_found() {
		String name = "test";
		opTag = Optional.of(tag);
		when(mockTagRepo.findByTagName(name)).thenReturn(opTag);
		
		Tag result = tagService.getTagByName(name);
		
		verify(mockTagRepo, times(1)).findByTagName(name);
		assertEquals(result, tag);
	}
	@Test
	void test_validateTagName_Not_Exists_throwsError(){
//		tagService
		// Setup
		when(mockTagRepo.findByTagName("abc")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> tagService.validateTagName("abc"))
				.isInstanceOf(NoDataFoundException.class);

	}


	@Test
	void test_validateTagsFromDto_DtoNotContain_ZeroTags_throwsError(){
      List<String> tags = List.of();
		assertThatThrownBy(() -> tagService.validateTagsFromDto(tags))
				.isInstanceOf(TagNotValidException.class);
	}

	@Test
	void test_validateTagsFromDto_DtoNotContain_Interview_Course_tag_throwsError(){
		List<String> tags = List.of("abx","123");
		assertThatThrownBy(() -> tagService.validateTagsFromDto(tags))
				.isInstanceOf(TagNotValidException.class);
	}

	@Test
	void test_getTagsFromDto(){
		List<String> tagList = List.of("interview","course");
		TagService tagServiceSpy = Mockito.spy(tagService);
		Mockito.doReturn(new Tag("interview")).when(tagServiceSpy).validateTagName("interview");
		Mockito.doReturn(new Tag("course")).when(tagServiceSpy).validateTagName("course");
		var list = tagServiceSpy.getTagsFromDto(tagList);
		assertEquals(tagList.size(),list.size());
	}
	@Test
	void test_findALLTags(){
		when(mockTagRepo.findAll()).thenReturn(List.of(new Tag("interview"),new Tag("course")));
		List<String> list = tagService.findAll();
		assertEquals(2,list.size());
		verify(mockTagRepo).findAll();
	}

	@Test
	void test_getTagById_idNotExists_ReturnEmptyString(){
		Long tagId = 0L;
		assertEquals(0,tagService.getTagById(tagId).length());
		verify(mockTagRepo).findById(tagId);


	}
	@Test
	void test_getTagById_success(){
		Long tagId = 0L;
		String tagName = "interview";
		when(mockTagRepo.findById(tagId)).thenReturn(Optional.of(new Tag(tagName)));
		assertEquals(tagName,tagService.getTagById(tagId));
		verify(mockTagRepo).findById(tagId);


	}

}
