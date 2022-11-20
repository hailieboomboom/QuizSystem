package com.fdmgroup.QuizSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;
import com.fdmgroup.QuizSystem.repository.QuizQuestionGradeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service that validate and processing QuizQuestionGrade related data.
 * @author Hailie
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class QuizQuestionGradeService {

	private final QuizQuestionGradeRepository qqgRepo;

	/**
	 * Persists QuizQuestionGrade to database.
	 * @param qqg The QuizQuestionGrade to persist
	 * @return QuizQuestionGrade that has been persisted in database
	 */
	public QuizQuestionGrade save(QuizQuestionGrade qqg) {
		return qqgRepo.save(qqg);
	}
	
	/**
	 * Finds QuizQuestionGrade by id
	 * @param qqgKey The primary key of QuizQuestionGrade
	 * @return QuizQuestionGrade that has been found in database
	 */
	public QuizQuestionGrade findById(QuizQuestionGradeKey qqgKey) {
		Optional<QuizQuestionGrade> foundQqg = qqgRepo.findByKey(qqgKey);
		if(foundQqg.isEmpty()) {
			return null;
		}
		return foundQqg.get();
	}

	/**
	 * Finds all the QuizQuestionGrade by quiz id
	 * @param quizId ID of the quiz to be found
	 * @return List of QuizQuetionGrade
	 */
	public List<QuizQuestionGrade> findAllByQuizId(long quizId) {
		return qqgRepo.findAllByQuizId(quizId);
	}
	
	/**
	 * Removes QuizQuestionGrade from database
	 * @param qqg  QuizQuestionGrade to be removed
	 */
	public void remove(QuizQuestionGrade qqg) {
		qqgRepo.delete(qqg);
	}
 
}
