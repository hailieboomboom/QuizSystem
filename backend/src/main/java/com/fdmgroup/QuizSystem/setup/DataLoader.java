package com.fdmgroup.QuizSystem.setup;

import javax.transaction.Transactional;

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

import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.TrainerService;

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

@Component
public class DataLoader implements ApplicationRunner {
     @Autowired
    private TrainerService trainerService;
    @Autowired
    private SalesService salesService;

    private Log log = LogFactory.getLog(DataLoader.class);
    
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private MultipleChoiceOptionService mcoService;

    @Autowired
    private TagService tagService;
  

    @Override
    @Transactional
    @Modifying
    public void run(ApplicationArguments args) throws Exception { 
        
        ////////// Load Users ////////////
        Trainer trainer = new Trainer();
        trainer.setUsername("Jason");
        trainer.setPassword("123");
        trainer.setEmail("123@gmail.com");
        trainer.setFirstname("JHJ");
        trainer.setLastname("Liu");
        trainer.setRole(Role.AUTHORISED_TRAINER);
        trainerService.save(trainer);
        System.out.println(trainerService.findByUsername("Jason"));
        
        Sales sales = new Sales();
        sales.setUsername("Yutta");
        sales.setPassword("321");
        sales.setEmail("321@gmail.com");
        sales.setFirstname("Yutta");
        sales.setLastname("Karima");
        sales.setRole(Role.AUTHORISED_SALES);
        salesService.save(sales);
        System.out.println(salesService.findByUsername("Yutta"));
        
        log.info("Finished setup");


        ////////// Load Questions ////////////

        MultipleChoiceQuestion mcq1 = new MultipleChoiceQuestion();
        mcq1.setQuestionDetails("test mcq1");
        MultipleChoiceOption mco1 = new MultipleChoiceOption("op1",true,mcq1);
        MultipleChoiceOption mco2 = new MultipleChoiceOption("op2",false,mcq1);
        MultipleChoiceOption mco3 = new MultipleChoiceOption("op3",false,mcq1);
        mcq1.setCreator(sales);

        ShortAnswerQuestion sa1 = new ShortAnswerQuestion();
        sa1.setQuestionDetails("what is the best colour");
        sa1.setCorrectAnswer("green is the best");
        sa1.setCreator(trainer);
        
        ShortAnswerQuestion sa2 = new ShortAnswerQuestion();
        sa2.setQuestionDetails("who is the best leader");
        sa2.setCorrectAnswer("bts");
        sa2.setCreator(trainer);

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        tag1.setTagName("course");
        tag2.setTagName("interview");
        
        tag1.addOneQuestion(mcq1);
        tag1.addOneQuestion(sa1);
        tag1.addOneQuestion(sa2);
        tag2.addOneQuestion(sa1);

        mcq1.addOneTag(tag1);
        sa1.addOneTag(tag2);
        sa1.addOneTag(tag1);
        sa2.addOneTag(tag1);

        tagService.save(tag1);
        tagService.save(tag2);

        questionService.save(mcq1);
        questionService.save(sa1);
        questionService.save(sa2);
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
