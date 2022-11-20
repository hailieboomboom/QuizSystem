package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.dto.McqDto.CorrectOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.McqOptionDto;
import com.fdmgroup.QuizSystem.exception.McqException.McqOptionNotValidException;
import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.repository.MultipleChoiceOptionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all CRUD and validation logic for multiple choice option
 */
@Service
@Transactional
@NoArgsConstructor
@AllArgsConstructor

public class MultipleChoiceOptionService {

	public static final String OPTION_DESCRIPTION_CANT_BE_EMPTY = "Option description can't be empty!";
	public static final String NOT_ENOUGH_OPTION = "Please provide at least more than one option";
	public static final String MULTIPLE_CORRECT_OPTION_ERROR = "Please only choose one correct option";
	public static final String QUESTION_NOT_FOUND = "Multiple Choice Question Not Found";
	public static final String OPTIONS_NOT_FOUNDED = "Options Not Founded for this question";
	@Autowired
	private MultipleChoiceOptionRepository mcoRepo;

	/**
	 * save multiple choice option to database
	 * @param mco mco need to be saved
	 * @return managed mco
	 */
	public MultipleChoiceOption save(MultipleChoiceOption mco) {
		return this.mcoRepo.save(mco);
	}

	/**
	 * save mcqOption to database
	 * @param mcqOptionDto mcqOption info
	 * @param mcqQuestion the question that the option is related to
	 * @return the manged option
	 */

	public MultipleChoiceOption createMcqOption(McqOptionDto mcqOptionDto, MultipleChoiceQuestion mcqQuestion){
		MultipleChoiceOption newOption = new MultipleChoiceOption();
		newOption.setCorrect(mcqOptionDto.isCorrect());
		newOption.setOptionDetail(mcqOptionDto.getOptionDescription());
		newOption.setMcq(mcqQuestion);
		return mcoRepo.save(newOption);
	}

	/**
	 * save a list of mcqOption to database
	 * @param mcqOptionDtoList a list of mcqDto
	 * @param mcqQuestion the question that the option is related to
	 * @return managedOption
	 */

	public List<MultipleChoiceOption> createListOfOption(List<McqOptionDto> mcqOptionDtoList, MultipleChoiceQuestion mcqQuestion){
		List<MultipleChoiceOption> optionList = new ArrayList<>();

		for(McqOptionDto mcqOption: mcqOptionDtoList) {
			optionList.add(createMcqOption(mcqOption, mcqQuestion));
		}


		return optionList;

	}

	/**
	 * validate options send from frontend.
	 * there should be more than one option but there can have one correct option.
	 * The description of every option can't be empty
	 * @param mcqOptionDtoList a list of options
	 *
	 */
	public void validateOptions(List<McqOptionDto> mcqOptionDtoList) {
		int numberOfCorrectOption = 0;

		if (mcqOptionDtoList.size() <= 1)
			throw new McqOptionNotValidException(NOT_ENOUGH_OPTION);
		for(McqOptionDto mcqOption: mcqOptionDtoList) {
			String optionDescription = mcqOption.getOptionDescription();
			if( optionDescription == null || optionDescription.isEmpty())
				throw new McqOptionNotValidException(OPTION_DESCRIPTION_CANT_BE_EMPTY);
			if (mcqOption.isCorrect())
				numberOfCorrectOption++;
		}
		if (numberOfCorrectOption !=1)
			throw new McqOptionNotValidException(MULTIPLE_CORRECT_OPTION_ERROR);
	}

	/**
	 * translate option to optionDto
	 * @param option the multiple choice option
	 * @return multiple choice option dto
	 */
	public McqOptionDto getMcqOptionDto(MultipleChoiceOption option){
		McqOptionDto mcqOptionDto = new McqOptionDto();
		mcqOptionDto.setOptionDescription(option.getOptionDetail());
		mcqOptionDto.setCorrect(option.isCorrect());
		mcqOptionDto.setId(option.getId());
		return mcqOptionDto;

	}

	/**
	 * get all options that belongs to a question
	 * @param questionId the question id
	 * @return a list of options that belongs to the question
	 */
	public List<McqOptionDto> listOptionsForMcq(long questionId){
		List<MultipleChoiceOption> optionList =  this.getMcqOption(questionId);
		List<McqOptionDto> optionDtoList = new ArrayList<>();
		for(MultipleChoiceOption option:optionList){
			optionDtoList.add(getMcqOptionDto(option));
		}
		return optionDtoList;
	}

	/**
	 * find all multiple choice option based on question id
	 * @param id question id
	 * @return a list of multiple choice option
	 */
	public List<MultipleChoiceOption> getMcqOption(Long id){
		return mcoRepo.findAllByMcqId(id);
	}

	/**
	 * find a multiple choice option based on option id
	 * @param id option id
	 * @return a multiple choice option
	 */
	public MultipleChoiceOption getMcqOptionById(Long id){
		if(mcoRepo.findById(id).isPresent())
		     return mcoRepo.findById(id).get();
		else
			return null;
	}


	/**
	 * Update all mcqOptions that belongs to a question
	 * @param mcqOptionDtoList  latest mcqOptions info
	 * @param originalMcq the outdated mcq in database
	 * @return a list of updated mcqOptions
	 */
	public List<MultipleChoiceOption> updateMcqOption(List<McqOptionDto> mcqOptionDtoList,MultipleChoiceQuestion originalMcq){
		long mcqId = originalMcq.getId();
		deleteQuestionOptions(mcqId);

		return createListOfOption(mcqOptionDtoList,originalMcq);

	}

	/**
	 * delete all options that belongs to a question
	 * @param questionId question id
	 */
	public void deleteQuestionOptions(long questionId){
		List<MultipleChoiceOption> options = getMcqOption(questionId);

		if(options == null) throw new NoDataFoundException(OPTIONS_NOT_FOUNDED);

		for (MultipleChoiceOption option:options)
			mcoRepo.delete(option);

	}

	/**
	 * get the right option of a question
	 * @param questionId question id
	 * @return  option dto that contains the correct option
	 */

	public CorrectOptionDto getRightOption(long questionId){
		List<MultipleChoiceOption> options = getMcqOption(questionId).stream().filter(option -> option.isCorrect()).toList();
		if(options.size() != 1)
			throw new McqOptionNotValidException(QUESTION_NOT_FOUND);

		MultipleChoiceOption option = options.get(0);
		CorrectOptionDto correctOptionDto = new CorrectOptionDto();
		correctOptionDto.setOptionId(option.getId());
		correctOptionDto.setOptionDescription(option.getOptionDetail());
		correctOptionDto.setCorrect(true);

		return correctOptionDto;

	}
}
