package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.dto.McqDto.CorrectOptionDto;
import com.fdmgroup.QuizSystem.dto.McqDto.McqOptionDto;
import com.fdmgroup.QuizSystem.exception.McqException.McqOptionNotValidException;
import com.fdmgroup.QuizSystem.exception.McqException.NoDataFoundException;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.repository.MultipleChoiceOptionRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class MultipleChoiceOptionService {

	@Autowired
	private MultipleChoiceOptionRepository mcoRepo;
	
	public MultipleChoiceOption save(MultipleChoiceOption mco) {
		return this.mcoRepo.save(mco);
	}


	public MultipleChoiceOption createMcqOption(McqOptionDto mcqOptionDto, MultipleChoiceQuestion mcqQuestion){
		MultipleChoiceOption newOption = new MultipleChoiceOption();
		newOption.setCorrect(mcqOptionDto.isCorrect());
		newOption.setOptionDetail(mcqOptionDto.getOptionDescription());
		newOption.setMcq(mcqQuestion);
		return mcoRepo.save(newOption);
	}

	public List<MultipleChoiceOption> createListOfOption(List<McqOptionDto> mcqOptionDtoList, MultipleChoiceQuestion mcqQuestion){
		List<MultipleChoiceOption> optionList = new ArrayList<>();
		validateOptions(mcqOptionDtoList);

		for(McqOptionDto mcqOption: mcqOptionDtoList) {
			optionList.add(createMcqOption(mcqOption, mcqQuestion));
		}



		return optionList;

	}

	public void validateOptions(List<McqOptionDto> mcqOptionDtoList) {
		int numberOfCorrectOption = 0;

		if (mcqOptionDtoList.size() <= 1)
			throw new McqOptionNotValidException("Please provide at least more than one option");
		for(McqOptionDto mcqOption: mcqOptionDtoList) {
			if (mcqOption.isCorrect())
				numberOfCorrectOption++;
		}
		if (numberOfCorrectOption !=1)
			throw new McqOptionNotValidException("Please only choose one correct option");
	}

	public McqOptionDto getMcqOptionDto(MultipleChoiceOption option){
		McqOptionDto mcqOptionDto = new McqOptionDto();
		mcqOptionDto.setOptionDescription(option.getOptionDetail());
		mcqOptionDto.setCorrect(option.isCorrect());
		mcqOptionDto.setId(option.getId());
		return mcqOptionDto;

	}

	public List<McqOptionDto> listOptionsForMcq(long questionId){
		List<MultipleChoiceOption> optionList =  this.getMcqOption(questionId);
		List<McqOptionDto> optionDtoList = new ArrayList<>();
		for(MultipleChoiceOption option:optionList){
			optionDtoList.add(getMcqOptionDto(option));
		}
		return optionDtoList;
	}

	public List<MultipleChoiceOption> getMcqOption(Long id){
		return mcoRepo.findAllByMcqId(id);
	}

	public MultipleChoiceOption getMcqOptionById(Long id){
		return mcoRepo.findById(id).get();// TODO: exception??
	}


	public List<MultipleChoiceOption> updateMcqOption(List<McqOptionDto> mcqOptionDtoList,long macId){

		List<MultipleChoiceOption> originalMcqOption = mcoRepo.findAllByMcqId(macId);
		if(originalMcqOption.size()!=mcqOptionDtoList.size()){
			throw new McqOptionNotValidException("You can't change the number of options after creating it");
		}
		int i = 0;
		int numberOfCorrectOption = 0;
		for (MultipleChoiceOption option:originalMcqOption){
			option.setCorrect(mcqOptionDtoList.get(i).isCorrect());
			option.setOptionDetail(mcqOptionDtoList.get(i).getOptionDescription());
			if(option.isCorrect()){
				numberOfCorrectOption++;
			}
		}
		if(numberOfCorrectOption!=1)
			throw new McqOptionNotValidException("Please choose only one correct option");

		return originalMcqOption;
	}

	public void deleteQuestionOptions(long questionId){
		List<MultipleChoiceOption> options = getMcqOption(questionId);

		if(options == null) throw new NoDataFoundException("Options Not Founded for this question");

		for (MultipleChoiceOption option:options)
			mcoRepo.delete(option);

	}

	public CorrectOptionDto getRightOption(long questionId){
		List<MultipleChoiceOption> options = getMcqOption(questionId).stream().filter(option -> option.isCorrect()).toList();
		if(options.size() != 1)
			throw new McqOptionNotValidException("Multiple Choice Question Not Found");

		MultipleChoiceOption option = options.get(0);
		CorrectOptionDto correctOptionDto = new CorrectOptionDto();
		correctOptionDto.setOptionId(option.getId());
		correctOptionDto.setOptionDescription(option.getOptionDetail());
		correctOptionDto.setCorrect(true);

		return correctOptionDto;

	}
}
