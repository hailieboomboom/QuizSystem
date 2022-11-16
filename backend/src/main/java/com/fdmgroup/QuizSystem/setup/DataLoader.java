package com.fdmgroup.QuizSystem.setup;

import javax.transaction.Transactional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fdmgroup.QuizSystem.model.MultipleChoiceOption;
import com.fdmgroup.QuizSystem.model.MultipleChoiceQuestion;
import com.fdmgroup.QuizSystem.model.Quiz;
import com.fdmgroup.QuizSystem.model.QuizAttempt;
import com.fdmgroup.QuizSystem.model.QuizCategory;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttempt;
import com.fdmgroup.QuizSystem.model.QuizQuestionMCQAttemptKey;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.ShortAnswerQuestion;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.model.Tag;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.repository.QuizAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizQuestionMCQAttemptRepository;
import com.fdmgroup.QuizSystem.repository.QuizRepository;
import com.fdmgroup.QuizSystem.service.MultipleChoiceOptionService;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizAttemptService;
import com.fdmgroup.QuizSystem.service.QuizService;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.StudentService;
import com.fdmgroup.QuizSystem.service.TagService;
import com.fdmgroup.QuizSystem.service.TrainerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final TrainerService trainerService;

    private final SalesService salesService;

	private final QuizAttemptRepository qaRepo;

	private final QuizRepository quizRepository;

	private final QuizQuestionMCQAttemptRepository mcqAttemptRepository;

    private final StudentService studentService;

    private final PasswordEncoder passwordEncoder;
    private final QuestionService questionService;
    private final QuizService quizService;
    private final MultipleChoiceOptionService mcoService;
    private final TagService tagService;
    private final QuizAttemptService quizAttemptService;


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

        Trainer unauthorisedTrainer1 = new Trainer();
        unauthorisedTrainer1.setUsername("ut1");
        unauthorisedTrainer1.setPassword(passwordEncoder.encode("1"));
        unauthorisedTrainer1.setEmail("12345@gmail.com");
        unauthorisedTrainer1.setFirstName("JHJ");
        unauthorisedTrainer1.setLastName("Liu");
        trainerService.save(unauthorisedTrainer1);
        System.out.println(trainerService.findByUsername("ut1"));

        Trainer unauthorisedTrainer2 = new Trainer();
        unauthorisedTrainer2.setUsername("ut2");
        unauthorisedTrainer2.setPassword(passwordEncoder.encode("1"));
        unauthorisedTrainer2.setEmail("123467@gmail.com");
        unauthorisedTrainer2.setFirstName("JHJ");
        unauthorisedTrainer2.setLastName("Liu");
        trainerService.save(unauthorisedTrainer2);
        System.out.println(trainerService.findByUsername("ut2"));

        Sales unauthorisedSales = new Sales();
        unauthorisedSales.setUsername("us");
        unauthorisedSales.setPassword(passwordEncoder.encode("1"));
        unauthorisedSales.setEmail("3210@gmail.com");
        unauthorisedSales.setFirstName("Yutta");
        unauthorisedSales.setLastName("Karima");
        salesService.save(unauthorisedSales);
        System.out.println(salesService.findByUsername("us"));

        Student student1 = new Student();
        student1.setUsername("skarima");
        student1.setPassword(passwordEncoder.encode("1"));
        student1.setEmail("student1@gmail.com");
        student1.setFirstName("student1");
        student1.setLastName("Karima");
        studentService.save(student1);


        // add pond student
        Student student2 = new Student();
        student2.setUsername("slong");
        student2.setPassword(passwordEncoder.encode("2"));
        student2.setEmail("student2@gmail.com");
        student2.setFirstName("student2");
        student2.setLastName("Long");
        student2.setRole(Role.POND);
        studentService.save(student2);


        // add beached student
        Student student3 = new Student();
        student3.setUsername("sbrown");
        student3.setPassword(passwordEncoder.encode("3"));
        student3.setEmail("student3@gmail.com");
        student3.setFirstName("student3");
        student3.setLastName("Brown");
        student3.setRole(Role.BEACHED);
        studentService.save(student3);


        log.info("Finished setup");



        ////////// Load Questions ////////////

        MultipleChoiceQuestion mcq1 = new MultipleChoiceQuestion();
        mcq1.setQuestionDetails("test mcq1");
        MultipleChoiceOption mco1 = new MultipleChoiceOption("op1 for 1",true,mcq1);
        MultipleChoiceOption mco2 = new MultipleChoiceOption("op2 for 2",false,mcq1);
        MultipleChoiceOption mco3 = new MultipleChoiceOption("op3 for 3",false,mcq1);
        mcq1.setCreator(trainer);


        MultipleChoiceQuestion mcq2 = new MultipleChoiceQuestion();
        mcq2.setQuestionDetails("test mcq2");

        MultipleChoiceOption mco11 = new MultipleChoiceOption("op1 for 2",true,mcq2);
        MultipleChoiceOption mco12 = new MultipleChoiceOption("op2 for 2",false,mcq2);
        MultipleChoiceOption mco13 = new MultipleChoiceOption("op3 for 2",false,mcq2);
        mcq2.setCreator(sales);

        MultipleChoiceQuestion mcq3 = new MultipleChoiceQuestion();
        mcq3.setQuestionDetails("test mcq3");
        MultipleChoiceOption mco111 = new MultipleChoiceOption("op1 for 3",true,mcq3);
        MultipleChoiceOption mco112 = new MultipleChoiceOption("op2 for 3",false,mcq3);
        MultipleChoiceOption mco113 = new MultipleChoiceOption("op3 for 3",false,mcq3);
        mcq3.setCreator(student1);


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



        Tag tagCourse = new Tag();
        Tag tagInterview = new Tag();
        Tag tagJava = new Tag();
        Tag tagPython = new Tag();
        Tag tagSpringBoot = new Tag();
        Tag tagSql = new Tag();
        Tag tagUnix = new Tag();
        Tag tagOod = new Tag();
        Tag tagJpa = new Tag();
        Tag tagSpring = new Tag();
        Tag tagReact = new Tag();
        Tag tagJavascript = new Tag();
        Tag tagProskills = new Tag();
        Tag tagWeb = new Tag();
        Tag tagAgile = new Tag();
        Tag tagOther = new Tag();
        Tag tagMcq = new Tag();
        tagCourse.setTagName("course");
        tagInterview.setTagName("interview");
        tagJava.setTagName("java");
        tagPython.setTagName("python");
        tagSpringBoot.setTagName("springboot");
        tagSql.setTagName("sql");
        tagUnix.setTagName("unix");
        tagOod.setTagName("ood");
        tagJpa.setTagName("jpa");
        tagSpring.setTagName("spring");
        tagReact.setTagName("react");
        tagJavascript.setTagName("javascript");
        tagProskills.setTagName("proskills");
        tagWeb.setTagName("web");
        tagAgile .setTagName("agile");
        tagOther.setTagName("other");
        tagMcq.setTagName("mcq");


        tagCourse.addOneQuestion(mcq1);
        tagCourse.addOneQuestion(mcq2); // adding tag1 & tag2 into mcq2
        tagCourse.addOneQuestion(sa1);
        tagCourse.addOneQuestion(sa2);
        tagCourse.addOneQuestion(sa3);
        tagCourse.addOneQuestion(sa4);
        tagInterview.addOneQuestion(mcq2);  // adding tag1 & tag2 into mcq2
        tagInterview.addOneQuestion(sa1);
        tagInterview.addOneQuestion(sa4);
        tagInterview.addOneQuestion(mcq2);
        tagCourse.addOneQuestion(mcq3);

        tagMcq.addOneQuestion(mcq1);
        tagMcq.addOneQuestion(mcq2);
        tagMcq.addOneQuestion(mcq3);

        mcq1.addOneTag(tagCourse);

        mcq2.addOneTag(tagInterview);
        mcq3.addOneTag(tagCourse);
        mcq1.addOneTag(tagMcq);
        mcq2.addOneTag(tagMcq);
        mcq3.addOneTag(tagMcq);
        sa1.addOneTag(tagInterview);

        sa1.addOneTag(tagCourse);
        sa2.addOneTag(tagCourse);
        sa3.addOneTag(tagCourse);
        sa4.addOneTag(tagCourse);
        sa4.addOneTag(tagInterview);


        // LOAD MCQ QUESTIONS AND SET TAGS
    	// add more 10 mcq course (5+2+3)
        //2 by beach student
        MultipleChoiceQuestion mcq4 = new MultipleChoiceQuestion();
        mcq4.setQuestionDetails("How could you create your own unchecked exception?");
        MultipleChoiceOption mco41 = new MultipleChoiceOption("create a class that extends Exception",false,mcq4);
        MultipleChoiceOption mco42 = new MultipleChoiceOption("create a class that extends Throwable",false,mcq4);
        MultipleChoiceOption mco43 = new MultipleChoiceOption("create a class that extends RuntimeException",true,mcq4);
        mcq4.setCreator(student3);
        mcq4.addOneTag(tagMcq);
        mcq4.addOneTag(tagCourse);
        mcq4.addOneTag(tagOod);
        tagCourse.addOneQuestion(mcq4);
        tagMcq.addOneQuestion(mcq4);
        tagOod.addOneQuestion(mcq4);
        
        MultipleChoiceQuestion mcq5 = new MultipleChoiceQuestion();
        mcq5.setQuestionDetails("What testing framework(s) are when() and verify() a part of?");
        MultipleChoiceOption mco51 = new MultipleChoiceOption("JUnit",false,mcq5);
        MultipleChoiceOption mco52 = new MultipleChoiceOption("JUnit and Mockito",false,mcq5);
        MultipleChoiceOption mco53 = new MultipleChoiceOption("Mockito",true,mcq5);
        mcq5.setCreator(student3);
        mcq5.addOneTag(tagMcq);
        mcq5.addOneTag(tagCourse);
        mcq5.addOneTag(tagOod);
        tagCourse.addOneQuestion(mcq5);
        tagMcq.addOneQuestion(mcq5);
        tagOod.addOneQuestion(mcq5);

        //2 by pond student
        MultipleChoiceQuestion mcq6 = new MultipleChoiceQuestion();
        mcq6.setQuestionDetails("Which method is not defined in the Object class?");
        MultipleChoiceOption mco61 = new MultipleChoiceOption("hashCode()",false,mcq6);
        MultipleChoiceOption mco62 = new MultipleChoiceOption("notify()",false,mcq6);
        MultipleChoiceOption mco63 = new MultipleChoiceOption("compareTo(Object o)",true,mcq6);
        mcq6.setCreator(student2);
        mcq6.addOneTag(tagMcq);
        mcq6.addOneTag(tagCourse);
        mcq6.addOneTag(tagOod);
        tagCourse.addOneQuestion(mcq6);
        tagMcq.addOneQuestion(mcq6);
        tagOod.addOneQuestion(mcq6);

        MultipleChoiceQuestion mcq7 = new MultipleChoiceQuestion();
        mcq7.setQuestionDetails("Is multiple inheritance possible in Java?");
        MultipleChoiceOption mco71 = new MultipleChoiceOption("No",false,mcq7);
        MultipleChoiceOption mco72 = new MultipleChoiceOption("Yes, only with interfaces",true,mcq7);
        MultipleChoiceOption mco73 = new MultipleChoiceOption("Yes, with both classes and interfaces",false,mcq7);
        mcq7.setCreator(student2);
        mcq7.addOneTag(tagMcq);
        mcq7.addOneTag(tagCourse);
        mcq7.addOneTag(tagOod);
        tagCourse.addOneQuestion(mcq7);
        tagMcq.addOneQuestion(mcq7);
        tagOod.addOneQuestion(mcq7);


        //1 by student 1
        MultipleChoiceQuestion mcq8 = new MultipleChoiceQuestion();
        mcq8.setQuestionDetails("Where can the static keyword be used?");
        MultipleChoiceOption mco81 = new MultipleChoiceOption("On variables",false,mcq8);
        MultipleChoiceOption mco82 = new MultipleChoiceOption("On methods",false,mcq8);
        MultipleChoiceOption mco83 = new MultipleChoiceOption("On variables, methods, and classes",true,mcq8);
        mcq8.setCreator(student1);
        mcq8.addOneTag(tagMcq);
        mcq8.addOneTag(tagCourse);
        mcq8.addOneTag(tagOod);
        tagCourse.addOneQuestion(mcq8);
        tagMcq.addOneQuestion(mcq8);
        tagOod.addOneQuestion(mcq8);
        
        // 5 by trainer
        MultipleChoiceQuestion mcq9 = new MultipleChoiceQuestion();
        mcq9.setQuestionDetails("In which form MYSQL query results are displayed?");
        MultipleChoiceOption mco91 = new MultipleChoiceOption("Rows and Columns",true,mcq9);
        MultipleChoiceOption mco92 = new MultipleChoiceOption("Lists and Tuples",false,mcq9);
        MultipleChoiceOption mco93 = new MultipleChoiceOption("Lists only",true,mcq9);
        mcq9.setCreator(trainer);
        mcq9.addOneTag(tagMcq);
        mcq9.addOneTag(tagCourse);
        mcq9.addOneTag(tagSql);
        tagCourse.addOneQuestion(mcq9);
        tagMcq.addOneQuestion(mcq9);
        tagSql.addOneQuestion(mcq9);
        
        MultipleChoiceQuestion mcq10 = new MultipleChoiceQuestion();
        mcq10.setQuestionDetails("ALTER command is a type of which SQL command?");
        MultipleChoiceOption mco101 = new MultipleChoiceOption("DML",false,mcq10);
        MultipleChoiceOption mco102 = new MultipleChoiceOption("DDL",true,mcq10);
        MultipleChoiceOption mco103 = new MultipleChoiceOption("DCL",false,mcq10);
        mcq10.setCreator(trainer);
        mcq10.addOneTag(tagMcq);
        mcq10.addOneTag(tagCourse);
        mcq10.addOneTag(tagSql);
        tagCourse.addOneQuestion(mcq10);
        tagMcq.addOneQuestion(mcq10);
        tagSql.addOneQuestion(mcq10);
        
        MultipleChoiceQuestion mcq11 = new MultipleChoiceQuestion();
        mcq11.setQuestionDetails("Which command is used to sort the lines of data in file in reverse order?");
        MultipleChoiceOption mco1111 = new MultipleChoiceOption("sort -r",true,mcq11);
        MultipleChoiceOption mco1112 = new MultipleChoiceOption("sh",false,mcq11);
        MultipleChoiceOption mco1113 = new MultipleChoiceOption("sort",false,mcq11);
        mcq11.setCreator(trainer);
        mcq11.addOneTag(tagMcq);
        mcq11.addOneTag(tagCourse);
        mcq11.addOneTag(tagUnix);
        tagCourse.addOneQuestion(mcq11);
        tagMcq.addOneQuestion(mcq11);
        tagUnix.addOneQuestion(mcq11);
        
        MultipleChoiceQuestion mcq12 = new MultipleChoiceQuestion();
        mcq12.setQuestionDetails("Which command is used to display the top of the file?");
        MultipleChoiceOption mco121 = new MultipleChoiceOption("grep",false,mcq12);
        MultipleChoiceOption mco122 = new MultipleChoiceOption("head",true,mcq12);
        MultipleChoiceOption mco123 = new MultipleChoiceOption("more",false,mcq12);
        mcq12.setCreator(trainer);
        mcq12.addOneTag(tagMcq);
        mcq12.addOneTag(tagCourse);
        mcq12.addOneTag(tagUnix);
        tagCourse.addOneQuestion(mcq12);
        tagMcq.addOneQuestion(mcq12);
        tagUnix.addOneQuestion(mcq12);
        
        MultipleChoiceQuestion mcq13 = new MultipleChoiceQuestion();
        mcq13.setQuestionDetails("What is the maximum length of a Python identifier?");
        MultipleChoiceOption mco131 = new MultipleChoiceOption("32",false,mcq13);
        MultipleChoiceOption mco132 = new MultipleChoiceOption("128",false,mcq13);
        MultipleChoiceOption mco133 = new MultipleChoiceOption("No fixed length specified",true,mcq13);
        mcq13.setCreator(trainer);
        mcq13.addOneTag(tagMcq);
        mcq13.addOneTag(tagCourse);
        mcq13.addOneTag(tagPython);
        tagCourse.addOneQuestion(mcq13);
        tagMcq.addOneQuestion(mcq13);
        tagPython.addOneQuestion(mcq13);


        // SAVE TAGS and QUESTIONS

        tagService.save(tagCourse);
        tagService.save(tagInterview);
        tagService.save(tagJava);
        tagService.save(tagPython);
        tagService.save(tagSpringBoot);
        tagService.save(tagSql);
        tagService.save(tagUnix);
        tagService.save(tagOod);
        tagService.save(tagJpa);
        tagService.save(tagSpring);
        tagService.save(tagReact);
        tagService.save(tagJavascript);
        tagService.save(tagProskills);
        tagService.save(tagWeb);
        tagService.save(tagAgile );
        tagService.save(tagOther);
        tagService.save(tagMcq);

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

        mcq3 = (MultipleChoiceQuestion) questionService.save(mcq3);
        mcoService.save(mco111);
        mcoService.save(mco112);
        mcoService.save(mco113);

        mcq5 = (MultipleChoiceQuestion) questionService.save(mcq5);
        mcoService.save(mco51);
        mcoService.save(mco52);
        mcoService.save(mco53);

        mcq6 = (MultipleChoiceQuestion) questionService.save(mcq6);
        mcoService.save(mco61);
        mcoService.save(mco62);
        mcoService.save(mco63);

        mcq7 = (MultipleChoiceQuestion) questionService.save(mcq7);
        mcoService.save(mco71);
        mcoService.save(mco72);
        mcoService.save(mco73);

        mcq8 = (MultipleChoiceQuestion) questionService.save(mcq8);
        mcoService.save(mco81);
        mcoService.save(mco82);
        mcoService.save(mco83);
        
        mcq9 = (MultipleChoiceQuestion) questionService.save(mcq9);
        mcoService.save(mco91);
        mcoService.save(mco92);
        mcoService.save(mco93);
        
        mcq10 = (MultipleChoiceQuestion) questionService.save(mcq10);
        mcoService.save(mco101);
        mcoService.save(mco102);
        mcoService.save(mco103);
        
        mcq11 = (MultipleChoiceQuestion) questionService.save(mcq11);
        mcoService.save(mco1111);
        mcoService.save(mco1112);
        mcoService.save(mco1113);
        
        mcq12 = (MultipleChoiceQuestion) questionService.save(mcq12);
        mcoService.save(mco121);
        mcoService.save(mco122);
        mcoService.save(mco123);
        
        mcq13 = (MultipleChoiceQuestion) questionService.save(mcq13);
        mcoService.save(mco131);
        mcoService.save(mco132);
        mcoService.save(mco133);



        ////////// Load Quizzes ////////////

//        QuizQuestionGrade qqg1 = new QuizQuestionGrade();
//        QuizQuestionGradeKey qqgKey = new QuizQuestionGradeKey((long) 1, (long) 1);
//        qqg1.setKey(qqgKey);
//        List<QuizQuestionGrade> qqgList = new ArrayList<QuizQuestionGrade>();
//        qqgList.add(qqg1);
//
        // male 2 quizzes

        Quiz quiz1 = new Quiz();
        quiz1.setCreator(trainer);
        quiz1.setName("Course quiz created by trainer id 1"); // fixed the name to interview quiz to match with quiz type
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ); // fixed the quiz type to match with question tags
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


        //quiz attempt

        ////// quiz attempt //////

        QuizAttempt qa1 = new QuizAttempt();
        QuizAttempt qa2 = new QuizAttempt();
        qa1.setQuiz(quiz1);
        qa1.setUser(student1);
        qa1.setAttemptNo(1);
        qa1.setTotalAwarded(0);
        qa2.setQuiz(quiz1);
        qa2.setUser(student1);
        qa2.setAttemptNo(2);
        qa2.setTotalAwarded(5);
        qaRepo.save(qa1);
        qaRepo.save(qa2);

        QuizQuestionMCQAttemptKey mcqAttemptKey = new QuizQuestionMCQAttemptKey(qa1.getId(), mcq1.getId());
        QuizQuestionMCQAttemptKey mcqAttemptKey2 = new QuizQuestionMCQAttemptKey(qa2.getId(), mcq1.getId());

        QuizQuestionMCQAttempt mcqAttempt1 = new QuizQuestionMCQAttempt();
        QuizQuestionMCQAttempt mcqAttempt2 = new QuizQuestionMCQAttempt();
        mcqAttempt1.setKey(mcqAttemptKey);
        mcqAttempt1.setAwarded_grade(0);
        mcqAttempt1.setQuizAttempt(qa1);
        mcqAttempt1.setMultipleChoiceQuestion(mcq1);
        mcqAttempt1.setSelectedOption(mco1);

        mcqAttempt2.setKey(mcqAttemptKey2);
        mcqAttempt2.setAwarded_grade(5);
        mcqAttempt2.setQuizAttempt(qa2);
        mcqAttempt2.setMultipleChoiceQuestion(mcq1);
        mcqAttempt2.setSelectedOption(mco2);
        mcqAttemptRepository.save(mcqAttempt1);
        mcqAttemptRepository.save(mcqAttempt2);

//        quizAttemptService.deleteAttempt(qa1);

        log.info("Finished setup");
        log.info("http://localhost:8088/QuizSystem");

    }
}
