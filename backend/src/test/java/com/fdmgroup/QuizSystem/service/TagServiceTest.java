package com.fdmgroup.QuizSystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.repository.TagRepository;

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

}
