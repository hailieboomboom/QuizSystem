package com.fdmgroup.QuizSystem.setup;

import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.service.MultipleChoiceOptionService;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.service.TagService;


@Component
public class DataLoader implements ApplicationRunner {
    
	@Autowired
    private QuestionService questionService;
    
    @Autowired
    private QuizService quizService;
    
    @Autowired
    private MultipleChoiceOptionService mcoService;
    
    
    @Autowired
    private TagService tagService;
    private Log log = LogFactory.getLog(DataLoader.class);

    @Override
    @Transactional
    @Modifying
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting setup");
        
        ////////// Load Users ////////////
        ////////// Load Questions ////////////

        MultipleChoiceQuestion mcq1 = new MultipleChoiceQuestion();
        mcq1.setQuestionDetails("test mcq1");
        MultipleChoiceOption mco1 = new MultipleChoiceOption("op1",true,mcq1);
        MultipleChoiceOption mco2 = new MultipleChoiceOption("op2",false,mcq1);
        MultipleChoiceOption mco3 = new MultipleChoiceOption("op3",false,mcq1);
        
        ShortAnswerQuestion sa1 = new ShortAnswerQuestion();
        sa1.setQuestionDetails("short answer");
        sa1.setCorrectAnswer("test answer");
        
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setTagName("course");
        tag2.setTagName("interview");
        
        mcq1.addOneTag(tag1);
        sa1.addOneTag(tag2);
        sa1.addOneTag(tag1);
       
        tagService.save(tag1);
        tagService.save(tag2);
        
        questionService.save(mcq1);
        questionService.save(sa1);
        mcoService.save(mco1);
        mcoService.save(mco2);
        mcoService.save(mco3);

        ////////// Load Quizzes ////////////
        
        
        Quiz courseQuiz1 = new Quiz(QuizCategory.COURSE_QUIZ, new ArrayList<Question>(Arrays.asList(mcq1,sa1)));
        quizService.save(courseQuiz1);
        log.info("--------------- All users ------------------------"); 
		log.info(quizService.getAllQuizzes()); 
       
        log.info("Finished setup");
        log.info("http://localhost:8088/QuizSystem");
    }
}
