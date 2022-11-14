package com.fdmgroup.QuizSystem.setup;

import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Question;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizCategory;

import com.fdmgroup.QuizSystem.model.QuizQuestionGrade;
import com.fdmgroup.QuizSystem.model.QuizQuestionGradeKey;

import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;

import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.service.MultipleChoiceOptionService;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizQuestionGradeService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.TagService;
import com.fdmgroup.QuizSystem.service.TrainerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import javax.transaction.Transactional;

import com.fdmgroup.QuizSystem.model.*;
import com.fdmgroup.QuizSystem.service.*;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import javax.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
 
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final TrainerService trainerService;

    private final SalesService salesService;


	private final QuizRepository quizRepository;

    private final StudentService studentService;

    private final PasswordEncoder passwordEncoder;
    private final QuestionService questionService;
    private final QuizService quizService;
    private final MultipleChoiceOptionService mcoService;
    private final TagService tagService;

    
 

    @Override
    @Transactional
    @Modifying
    public void run(ApplicationArguments args) throws Exception { 
        
        ////////// Load Users ////////////
        Trainer trainer = new Trainer();
        trainer.setUsername("Jason");
        trainer.setPassword(passwordEncoder.encode("1"));
        trainer.setEmail("123@gmail.com");
        trainer.setFirstName("JHJ");
        trainer.setLastName("Liu");
        trainer.setRole(Role.AUTHORISED_TRAINER);
        trainerService.save(trainer);
        System.out.println(trainerService.findByUsername("Jason"));
        
        Sales sales = new Sales();
        sales.setUsername("Yutta");
        sales.setPassword(passwordEncoder.encode("1"));
        sales.setEmail("321@gmail.com");
        sales.setFirstName("Yutta");
        sales.setLastName("Karima");
        sales.setRole(Role.AUTHORISED_SALES);
        salesService.save(sales);
        System.out.println(salesService.findByUsername("Yutta"));

        Trainer unauthorisedTrainer = new Trainer();
        unauthorisedTrainer.setUsername("ut");
        unauthorisedTrainer.setPassword(passwordEncoder.encode("1"));
        unauthorisedTrainer.setEmail("1234@gmail.com");
        unauthorisedTrainer.setFirstName("JHJ");
        unauthorisedTrainer.setLastName("Liu");
        trainerService.save(unauthorisedTrainer);
        System.out.println(trainerService.findByUsername("ut"));

        Sales unauthorisedSales = new Sales();
        unauthorisedSales.setUsername("us");
        unauthorisedSales.setPassword(passwordEncoder.encode("1"));
        unauthorisedSales.setEmail("3210@gmail.com");
        unauthorisedSales.setFirstName("Yutta");
        unauthorisedSales.setLastName("Karima");
        salesService.save(unauthorisedSales);
        System.out.println(salesService.findByUsername("us"));

        Student student1 = new Student();
        student1.setUsername("ts");
        student1.setPassword(passwordEncoder.encode("1"));
        student1.setEmail("student1@gmail.com");
        student1.setFirstName("student1");
        student1.setLastName("Karima");
        studentService.save(student1);
        System.out.println(salesService.findByUsername("us"));

        log.info("Finished setup");


        ////////// Load Questions ////////////

        MultipleChoiceQuestion mcq1 = new MultipleChoiceQuestion();
        mcq1.setQuestionDetails("test mcq1");
        MultipleChoiceOption mco1 = new MultipleChoiceOption("op1",true,mcq1);
        MultipleChoiceOption mco2 = new MultipleChoiceOption("op2",false,mcq1);
        MultipleChoiceOption mco3 = new MultipleChoiceOption("op3",false,mcq1);
        mcq1.setCreator(sales);
        

        MultipleChoiceQuestion mcq2 = new MultipleChoiceQuestion();
        mcq2.setQuestionDetails("test mcq2");
        MultipleChoiceOption mco11 = new MultipleChoiceOption("op1",true,mcq2);
        MultipleChoiceOption mco12 = new MultipleChoiceOption("op2",false,mcq2);
        MultipleChoiceOption mco13 = new MultipleChoiceOption("op3",false,mcq2);
        mcq2.setCreator(sales);

        ShortAnswerQuestion sa1 = new ShortAnswerQuestion();
        sa1.setQuestionDetails("what is the best colour");
        sa1.setCorrectAnswer("green is the best");
        sa1.setCreator(trainer);
        
        ShortAnswerQuestion sa2 = new ShortAnswerQuestion();
        sa2.setQuestionDetails("who is the best leader");
        sa2.setCorrectAnswer("bts");
        sa2.setCreator(sales);
        
        ShortAnswerQuestion sa3 = new ShortAnswerQuestion();
        sa3.setQuestionDetails("where is the best city");
        sa3.setCorrectAnswer("nyc");
        sa3.setCreator(sales);
        
        ShortAnswerQuestion sa4 = new ShortAnswerQuestion();
        sa4.setQuestionDetails("what is the best language");
        sa4.setCorrectAnswer("java");
        sa4.setCreator(trainer);
        
         // tag: interview course python java web springboot sql unix ood jpa spring react javascript proskill other
		

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        Tag tag4 = new Tag();
        Tag tag5 = new Tag();
        Tag tag6 = new Tag();
        Tag tag7 = new Tag();
        Tag tag8 = new Tag();
        Tag tag9 = new Tag();
        Tag tag10 = new Tag();
        Tag tag11 = new Tag();
        Tag tag12 = new Tag();
        Tag tag13 = new Tag();
        Tag tag14 = new Tag();
        Tag tag15 = new Tag();
        Tag tag16 = new Tag();
        tag1.setTagName("course");
        tag2.setTagName("interview");
        tag3.setTagName("java");
        tag4.setTagName("python");
        tag5.setTagName("springboot");
        tag6.setTagName("sql");
        tag7.setTagName("unix");
        tag8.setTagName("ood");
        tag9.setTagName("jpa");
        tag10.setTagName("spring");
        tag11.setTagName("react");
        tag12.setTagName("javascript");
        tag13.setTagName("proskill");
        tag14.setTagName("web");
        tag15.setTagName("agile");
        tag16.setTagName("other");
        
        
        tag1.addOneQuestion(mcq1);
        tag1.addOneQuestion(sa1);
        tag1.addOneQuestion(sa2);
        tag1.addOneQuestion(sa3);
        tag1.addOneQuestion(sa4);
        tag2.addOneQuestion(sa1);
        tag2.addOneQuestion(sa4);

        mcq1.addOneTag(tag1);
        sa1.addOneTag(tag2);
        sa1.addOneTag(tag1);
        sa2.addOneTag(tag1);
        sa3.addOneTag(tag1);
        sa4.addOneTag(tag1);
        sa4.addOneTag(tag2);

        tagService.save(tag1);
        tagService.save(tag2);
        tagService.save(tag3);
        tagService.save(tag4);
        tagService.save(tag5);
        tagService.save(tag6);
        tagService.save(tag7);
        tagService.save(tag8);
        tagService.save(tag9);
        tagService.save(tag10);
        tagService.save(tag11);
        tagService.save(tag12);
        tagService.save(tag13);
        tagService.save(tag14);
        tagService.save(tag15);
        tagService.save(tag16);

        mcq1 = (MultipleChoiceQuestion) questionService.save(mcq1);
        questionService.save(sa1);
        questionService.save(sa2);
        questionService.save(sa3);
        questionService.save(sa4);
        mcoService.save(mco1);
        mcoService.save(mco2);
        mcoService.save(mco3);
        
        
        mcq2 = (MultipleChoiceQuestion) questionService.save(mcq2);
        mcoService.save(mco11);
        mcoService.save(mco12);
        mcoService.save(mco13);

        ////////// Load Quizzes ////////////

//        QuizQuestionGrade qqg1 = new QuizQuestionGrade();
//        QuizQuestionGradeKey qqgKey = new QuizQuestionGradeKey((long) 1, (long) 1);
//        qqg1.setKey(qqgKey);
//        List<QuizQuestionGrade> qqgList = new ArrayList<QuizQuestionGrade>();
//        qqgList.add(qqg1);
//        
        Quiz quiz1 = new Quiz();
        quiz1.setCreator(trainer);
        quiz1.setName("course quiz 1");
        quiz1.setQuizCategory(QuizCategory.INTERVIEW_QUIZ);
        quiz1 = quizService.save(quiz1);
       
        quizService.addQuestionIntoQuiz(mcq1, quiz1, (float)5.0);

        quizService.addQuestionIntoQuiz(mcq2, quiz1, (float)6.0);

        System.out.println("--------SAVE QUIZ1 DONE-------");
//        quizService.removeQuestionFromQuiz(mcq1, quiz1);
//        System.out.println("--------REMOVE MCQ1 AND QUIZ1 WITH GRADE DONE-------");
//

////        quiz1.setQuizQuestionsGrade(qqgList);
////        
////        mcq1.setQuizQuestionsGrade(qqgList);
//        quizService.addQuestion(mcq1, quiz1);
// 
//        
//        questionService.save(mcq1);
//        quizService.save(quiz1);


//        Quiz courseQuiz1 = new Quiz(QuizCategory.COURSE_QUIZ, new ArrayList<Question>(Arrays.asList(mcq1,sa1)));
//        quizService.save(courseQuiz1);
        log.info("--------------- All users ------------------------");
//        log.info(quizService.getAllQuizzes());

        // TO SUMMER: QUIZ CONSTRUCTOR HAS BEEN UPDATED
//		Quiz courseQuiz1 = new Quiz("course quiz 1", QuizCategory.COURSE_QUIZ, new ArrayList<Question>(Arrays.asList(mcq1, sa1)), trainer);
//		Quiz interviewQuiz1 = new Quiz("interview quiz 1", QuizCategory.INTERVIEW_QUIZ, new ArrayList<Question>(Arrays.asList(mcq1)), sales);
//		quizRepository.save(courseQuiz1);
//		quizRepository.save(interviewQuiz1);


//		// let question know about quiz
//		mcq1.setQuizzes(new ArrayList<Quiz>(Arrays.asList(courseQuiz1,interviewQuiz1 )));
//		sa1.setQuizzes(new ArrayList<Quiz>(Arrays.asList(interviewQuiz1 )));
//		questionService.save(mcq1);
//		questionService.save(sa1);
		
//		// let user know about quiz
//		trainer.setQuizzes(new ArrayList<Quiz>(Arrays.asList(courseQuiz1)));
//		sales.setQuizzes(new ArrayList<Quiz>(Arrays.asList(interviewQuiz1)));
//		userService.save(trainer);
//		userService.save(sales);

//        log.info("--------------- All users ------------------------");
//        log.info(quizService.getAllQuizzes());



        log.info("Finished setup");
        log.info("http://localhost:8088/QuizSystem");
        
    }
}