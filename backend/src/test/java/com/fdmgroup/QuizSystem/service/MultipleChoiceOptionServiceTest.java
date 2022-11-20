package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.dto.McqDto.CorrectOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.McqOptionDto;
import com.fdmgroup.QuizSystem.exception.McqException.McqOptionNotValidException;
import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.exception.McqException.NotEnoughAccessException;
import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.repository.MultipleChoiceOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MultipleChoiceOptionServiceTest {

    @Mock
    private MultipleChoiceOptionRepository mockMcoRepo;

    private MultipleChoiceOption mco;
    private MultipleChoiceQuestion question;


    private MultipleChoiceOptionService multipleChoiceOptionService;
    @BeforeEach
    void setUp(){
        multipleChoiceOptionService = new MultipleChoiceOptionService(mockMcoRepo);
        question = new MultipleChoiceQuestion();
        question.setId(0L);
        MultipleChoiceOption option = new MultipleChoiceOption();
        option.setId(0L);
        option.setOptionDetail("optionDescription");
        option.setCorrect(false);
        MultipleChoiceOption option2 = new MultipleChoiceOption();
        option.setId(1L);
        option.setOptionDetail("optionDescription");
        option.setCorrect(true);
        question.setMcoptions(List.of(option,option2));
        mco = new MultipleChoiceOption(0L, "optionDescription", false, question);


    }

    @Test
    void test_Save() {
        // Setup

        // Run the test
         multipleChoiceOptionService.save(mco);

        // Verify the results
        verify(mockMcoRepo).save(mco);
    }

    @Test
    void test_CreateMcqOption() {
        // Setup
        McqOptionDto mcqOptionDto = new McqOptionDto(0L, "optionDescription", false);

        // Run the test
         multipleChoiceOptionService.createMcqOption(mcqOptionDto,
                question);

        // Verify the results
        verify(mockMcoRepo).save(any(MultipleChoiceOption.class));
    }

    @Test
    void test_CreateListOfOption() {
        // Setup
        var option1 = new McqOptionDto(1L, "optionDescription1", false);
        var option2 = new McqOptionDto(2L, "optionDescription2", true);
        var mco1 = new MultipleChoiceOption(1L, "optionDescription1", false, question);
        var mco2 = new MultipleChoiceOption(2L, "optionDescription2", true, question);
        final List<McqOptionDto> mcqOptionDtoList = List.of(option1,option2);
        MultipleChoiceOptionService mcoServiceSpy = Mockito.spy(multipleChoiceOptionService);
        Mockito.doReturn(mco1).when(mcoServiceSpy).createMcqOption(option1,question);
        Mockito.doReturn(mco2).when(mcoServiceSpy).createMcqOption(option2,question);
        mcoServiceSpy.createListOfOption(mcqOptionDtoList,question);
        verify(mcoServiceSpy,times(2)).createMcqOption(any(McqOptionDto.class),question);


    }


    @Test
    void test_ValidateOptions_NotEnoughOption_throwsError() {
        var option1 = new McqOptionDto(1L, "optionDescription1", false);

        // Setup
        final List<McqOptionDto> mcqOptionDtoList = List.of(option1);

        // Run the test
//        multipleChoiceOptionService.validateOptions(mcqOptionDtoList);
        assertThatThrownBy(() -> multipleChoiceOptionService.validateOptions(mcqOptionDtoList)).isInstanceOf(McqOptionNotValidException.class);

        // Verify the results
    }

    @Test
    void test_ValidateOptions_MultipleCorrectOption_throwsError() {
        var option1 = new McqOptionDto(1L, "optionDescription1", true);
        var option2 = new McqOptionDto(2L, "optionDescription2", true);

        // Setup
        final List<McqOptionDto> mcqOptionDtoList = List.of(option1,option2);

        // Run the test
//        multipleChoiceOptionService.validateOptions(mcqOptionDtoList);
        assertThatThrownBy(() -> multipleChoiceOptionService.validateOptions(mcqOptionDtoList)).isInstanceOf(McqOptionNotValidException.class);

        // Verify the results
    }

    @Test
    void test_ValidateOptions_emptyOptions_throwsError() {
        var option1 = new McqOptionDto(1L, "", true);
        var option2 = new McqOptionDto(2L, "optionDescription2", false);

        // Setup
        final List<McqOptionDto> mcqOptionDtoList = List.of(option1,option2);

        // Run the test
//        multipleChoiceOptionService.validateOptions(mcqOptionDtoList);
        assertThatThrownBy(() -> multipleChoiceOptionService.validateOptions(mcqOptionDtoList)).isInstanceOf(McqOptionNotValidException.class);

        // Verify the results
    }

    @Test
    void test_ValidateOptions_success() {
        var option1 = new McqOptionDto(1L, "optionDescription1", false);
        var option2 = new McqOptionDto(2L, "optionDescription2", false);
        var option3 = new McqOptionDto(3L, "optionDescription3", true);
        // Setup
        final List<McqOptionDto> mcqOptionDtoList = List.of(option1,option2,option3);

        // Run the test
        multipleChoiceOptionService.validateOptions(mcqOptionDtoList);

        // Verify the results
    }

    @Test
    void test_GetMcqOptionDto() {
        // Setup

        // Run the test
        final McqOptionDto result = multipleChoiceOptionService.getMcqOptionDto(mco);

        // Verify the results
        assertEquals(mco.getOptionDetail(),result.getOptionDescription());
        assertEquals(mco.isCorrect(),result.isCorrect());

    }

    @Test
    void test_ListOptionsForMcq() {
        // Setup
        // Configure MultipleChoiceOptionRepository.findAllByMcqId(...).
        final List<MultipleChoiceOption> options = List.of(
                new MultipleChoiceOption(0L, "optionDescription", false, question),
                new MultipleChoiceOption(1L, "optionDescription2", true, question));
        when(mockMcoRepo.findAllByMcqId(0L)).thenReturn(options);

        // Run the test
        final List<McqOptionDto> result = multipleChoiceOptionService.listOptionsForMcq(0L);

        // Verify the results
        assertEquals(2,result.size());
    }


    @Test
    void test_ListOptionsForMcq_MultipleChoiceOptionRepository_ReturnsNull() {
        // Setup
        when(mockMcoRepo.findAllByMcqId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<McqOptionDto> result = multipleChoiceOptionService.listOptionsForMcq(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void test_GetMcqOption() {
        // Setup
        // Configure MultipleChoiceOptionRepository.findAllByMcqId(...).
        final List<MultipleChoiceOption> options = List.of(
                new MultipleChoiceOption(0L, "optionDescription", false, question),
                new MultipleChoiceOption(1L, "optionDescription2", true, question));
        when(mockMcoRepo.findAllByMcqId(0L)).thenReturn(options);

        // Run the test
        final List<MultipleChoiceOption> result = multipleChoiceOptionService.getMcqOption(0L);

        // Verify the results
        assertEquals(2,result.size());
    }

    @Test
    void test_GetMcqOption_MultipleChoiceOptionRepository_ReturnsNull() {
        // Setup
        when(mockMcoRepo.findAllByMcqId(0L)).thenReturn(null);

        // Run the test
        final List<MultipleChoiceOption> result = multipleChoiceOptionService.getMcqOption(0L);

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void test_GetMcqOptionById() {
        // Setup
        // Configure MultipleChoiceOptionRepository.findById(...).
        final Optional<MultipleChoiceOption> multipleChoiceOption = Optional.of(
                new MultipleChoiceOption(0L, "optionDescription", false, question));
        when(mockMcoRepo.findById(0L)).thenReturn(multipleChoiceOption);

        // Run the test
        final MultipleChoiceOption result = multipleChoiceOptionService.getMcqOptionById(0L);

        // Verify the results
        verify(mockMcoRepo).findById(0L);
    }

    @Test
    void test_GetMcqOptionById_MultipleChoiceOptionRepository_throwsError() {
        // Setup
        when(mockMcoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> multipleChoiceOptionService.getMcqOptionById(0L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testUpdateMcqOption() {
        // Setup
        final List<McqOptionDto> mcqOptionDtoList = List.of(new McqOptionDto(0L, "optionDescription", false));
        MultipleChoiceOptionService mcoServiceSpy = Mockito.spy(multipleChoiceOptionService);
        Mockito.doNothing().when(mcoServiceSpy).deleteQuestionOptions(question.getId());
        Mockito.doReturn(mcqOptionDtoList).when(mcoServiceSpy).createListOfOption(mcqOptionDtoList,
                question);
        // Run the test
        final List<MultipleChoiceOption> result = mcoServiceSpy.updateMcqOption(mcqOptionDtoList,
                question);

        // Verify the results
        assertEquals(1,result.size());
        verify(mcoServiceSpy).deleteQuestionOptions(question.getId());
        verify(mcoServiceSpy).createListOfOption(mcqOptionDtoList, question);
    }

    @Test
    void testDeleteQuestionOptions() {
        // Setup
        // Configure MultipleChoiceOptionRepository.findAllByMcqId(...).

        final List<MultipleChoiceOption> options = List.of(
                new MultipleChoiceOption(0L, "optionDescription", false, question));
        when(mockMcoRepo.findAllByMcqId(0L)).thenReturn(options);

        // Run the test
        multipleChoiceOptionService.deleteQuestionOptions(0L);

        // Verify the results
        verify(mockMcoRepo).delete(any(MultipleChoiceOption.class));
    }

    @Test
    void testDeleteQuestionOptions_MultipleChoiceOptionRepositoryFindAllByMcqIdReturnsNull() {
        // Setup
        when(mockMcoRepo.findAllByMcqId(0L)).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> multipleChoiceOptionService.deleteQuestionOptions(0L))
                .isInstanceOf(NoDataFoundException.class);
    }



    @Test
    void testGetRightOption() {
        // Setup
        // Configure MultipleChoiceOptionRepository.findAllByMcqId(...).
        var option1 = new MultipleChoiceOption(0L, "optionDescription", false, question);
        var option2 = new MultipleChoiceOption(1L, "optionDescription2", true, question);

        final List<MultipleChoiceOption> options = List.of(option1,option2
                );
        when(mockMcoRepo.findAllByMcqId(0L)).thenReturn(options);

        // Run the test
        final CorrectOptionDto result = multipleChoiceOptionService.getRightOption(0L);

        // Verify the results
        assertEquals(result.getOptionDescription(),option2.getOptionDetail());
    }

}
