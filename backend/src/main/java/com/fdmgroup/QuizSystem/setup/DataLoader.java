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
        MultipleChoiceOption mco1 = new MultipleChoiceOption("op1",true,mcq1);
        MultipleChoiceOption mco2 = new MultipleChoiceOption("op2",false,mcq1);
        MultipleChoiceOption mco3 = new MultipleChoiceOption("op3",false,mcq1);
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
        MultipleChoiceQuestion mcq5 = new MultipleChoiceQuestion();
        mcq5.setQuestionDetails("What testing framework(s) are when() and verify() a part of?");
        MultipleChoiceOption mco51 = new MultipleChoiceOption("JUnit",false,mcq5);
        MultipleChoiceOption mco52 = new MultipleChoiceOption("JUnit and Mockito",false,mcq5);
        MultipleChoiceOption mco53 = new MultipleChoiceOption("Mockito",true,mcq5);
        mcq5.setCreator(student2);
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

        
        // LOAD MCQ QUESTIONS AND SET TAGS
        // Interview based, tagInterview, tagMcq
        
        // 5 by sales
        MultipleChoiceQuestion mcqIntv1 = new MultipleChoiceQuestion();
        mcqIntv1.setQuestionDetails("Under which pillar of OOPS do base class and derived class relationships come?");
        MultipleChoiceOption mcoIntv1_1 = new MultipleChoiceOption("Polymorphism", false, mcqIntv1);
        MultipleChoiceOption mcoIntv1_2 = new MultipleChoiceOption("Inheritance", true, mcqIntv1);
        MultipleChoiceOption mcoIntv1_3 = new MultipleChoiceOption("Encapsulation",false, mcqIntv1);
        MultipleChoiceOption mcoIntv1_4 = new MultipleChoiceOption("Abstraction",false, mcqIntv1);
        mcqIntv1.setCreator(sales);
        mcqIntv1.addOneTag(tagMcq);
        mcqIntv1.addOneTag(tagInterview);
        mcqIntv1.addOneTag(tagOod);
        tagInterview.addOneQuestion(mcqIntv1);
        tagMcq.addOneQuestion(mcqIntv1);
        tagOod.addOneQuestion(mcqIntv1);

        
        MultipleChoiceQuestion mcqIntv2 = new MultipleChoiceQuestion();
        mcqIntv2.setQuestionDetails("What is the number of parameters that a default constructor requires?");
        MultipleChoiceOption mcoIntv2_1 = new MultipleChoiceOption("0",true, mcqIntv2);
        MultipleChoiceOption mcoIntv2_2 = new MultipleChoiceOption("1",false, mcqIntv2);
        MultipleChoiceOption mcoIntv2_3 = new MultipleChoiceOption("2",false, mcqIntv2);
        MultipleChoiceOption mcoIntv2_4 = new MultipleChoiceOption("3",false, mcqIntv2);
        mcqIntv2.setCreator(sales);
        mcqIntv2.addOneTag(tagMcq);
        mcqIntv2.addOneTag(tagInterview);
        mcqIntv2.addOneTag(tagOod);
        tagInterview.addOneQuestion(mcqIntv2);
        tagMcq.addOneQuestion(mcqIntv2);
        tagOod.addOneQuestion(mcqIntv2);

        MultipleChoiceQuestion mcqIntv3 = new MultipleChoiceQuestion();
        mcqIntv3.setQuestionDetails("Annotation to find a transaction and then fail, complaining that no Hibernate session’s been bound to the thread.");
        MultipleChoiceOption mcoIntv3_1 = new MultipleChoiceOption("Transaction", false, mcqIntv3);
        MultipleChoiceOption mcoIntv3_2 = new MultipleChoiceOption("Transactional", true, mcqIntv3);
        MultipleChoiceOption mcoIntv3_3 = new MultipleChoiceOption("Transactions",false, mcqIntv3);
        MultipleChoiceOption mcoIntv3_4 = new MultipleChoiceOption("None of the mentioned",false, mcqIntv3);
        mcqIntv3.setCreator(sales);
        mcqIntv3.addOneTag(tagMcq);
        mcqIntv3.addOneTag(tagInterview);
        mcqIntv3.addOneTag(tagJpa);
        tagInterview.addOneQuestion(mcqIntv3);
        tagMcq.addOneQuestion(mcqIntv3);
        tagJpa.addOneQuestion(mcqIntv3);
        
        MultipleChoiceQuestion mcqIntv4 = new MultipleChoiceQuestion();
        mcqIntv4.setQuestionDetails("In which access should a constructor be defined, so that object of the class can be created in any function?");
        MultipleChoiceOption mcoIntv4_1 = new MultipleChoiceOption("Any access specifier will work", false, mcqIntv4);
        MultipleChoiceOption mcoIntv4_2 = new MultipleChoiceOption("Private", false, mcqIntv4);
        MultipleChoiceOption mcoIntv4_3 = new MultipleChoiceOption("Public",true, mcqIntv4);
        MultipleChoiceOption mcoIntv4_4 = new MultipleChoiceOption("Protected",false, mcqIntv4);
        mcqIntv4.setCreator(sales);
        mcqIntv4.addOneTag(tagMcq);
        mcqIntv4.addOneTag(tagInterview);
        mcqIntv4.addOneTag(tagOod);
        tagInterview.addOneQuestion(mcqIntv4);
        tagMcq.addOneQuestion(mcqIntv4);
        tagOod.addOneQuestion(mcqIntv4);
        
        
        MultipleChoiceQuestion mcqIntv5 = new MultipleChoiceQuestion();
        mcqIntv5.setQuestionDetails("Which of the following is used to find and fix bugs in the Java programs?");
        MultipleChoiceOption mcoIntv5_1 = new MultipleChoiceOption("JVM", false, mcqIntv5);
        MultipleChoiceOption mcoIntv5_2 = new MultipleChoiceOption("JRE", false, mcqIntv5);
        MultipleChoiceOption mcoIntv5_3 = new MultipleChoiceOption("JDK",false, mcqIntv5);
        MultipleChoiceOption mcoIntv5_4 = new MultipleChoiceOption("JDB",true, mcqIntv5);
        mcqIntv5.setCreator(sales);
        mcqIntv5.addOneTag(tagMcq);
        mcqIntv5.addOneTag(tagInterview);
        mcqIntv5.addOneTag(tagJava);
        tagInterview.addOneQuestion(mcqIntv5);
        tagMcq.addOneQuestion(mcqIntv5);
        tagJava.addOneQuestion(mcqIntv5);
        
        
        // 2 by pond student
        MultipleChoiceQuestion mcqIntv6 = new MultipleChoiceQuestion();
        mcqIntv6.setQuestionDetails("Which method of the Class.class is used to determine the name of a class represented by the class object as a String?");
        MultipleChoiceOption mcoIntv6_1 = new MultipleChoiceOption("getClass()", false, mcqIntv6);
        MultipleChoiceOption mcoIntv6_2 = new MultipleChoiceOption("intern()", false, mcqIntv6);
        MultipleChoiceOption mcoIntv6_3 = new MultipleChoiceOption("getName()",true, mcqIntv6);
        MultipleChoiceOption mcoIntv6_4 = new MultipleChoiceOption("toString()",false, mcqIntv6);
        mcqIntv6.setCreator(student2);
        mcqIntv6.addOneTag(tagMcq);
        mcqIntv6.addOneTag(tagInterview);
        mcqIntv6.addOneTag(tagJava);
        tagInterview.addOneQuestion(mcqIntv6);
        tagMcq.addOneQuestion(mcqIntv6);
        tagJava.addOneQuestion(mcqIntv6);
        
        MultipleChoiceQuestion mcqIntv7 = new MultipleChoiceQuestion();
        mcqIntv7.setQuestionDetails("Which option is false about the final keyword?");
        MultipleChoiceOption mcoIntv7_1 = new MultipleChoiceOption("A final method cannot be overridden in its subclasses.", false, mcqIntv7);
        MultipleChoiceOption mcoIntv7_2 = new MultipleChoiceOption("A final class cannot be extended.", false, mcqIntv7);
        MultipleChoiceOption mcoIntv7_3 = new MultipleChoiceOption("A final class cannot extend other classes.",true, mcqIntv7);
        MultipleChoiceOption mcoIntv7_4 = new MultipleChoiceOption("A final method can be inherited.",false, mcqIntv7);
        mcqIntv7.setCreator(student2);
        mcqIntv7.addOneTag(tagMcq);
        mcqIntv7.addOneTag(tagInterview);
        mcqIntv7.addOneTag(tagJava);
        tagInterview.addOneQuestion(mcqIntv7);
        tagMcq.addOneQuestion(mcqIntv7);
        tagJava.addOneQuestion(mcqIntv7);
        
        
        // 3 by beached student
        MultipleChoiceQuestion mcqIntv8 = new MultipleChoiceQuestion();
        mcqIntv8.setQuestionDetails("Who is responsible for sprint meeting?");
        MultipleChoiceOption mcoIntv8_1 = new MultipleChoiceOption("Product owner", false, mcqIntv8);
        MultipleChoiceOption mcoIntv8_2 = new MultipleChoiceOption("Scrum team", false, mcqIntv8);
        MultipleChoiceOption mcoIntv8_3 = new MultipleChoiceOption("Scrum master",true, mcqIntv8);
        MultipleChoiceOption mcoIntv8_4 = new MultipleChoiceOption("All of the above",false, mcqIntv8);
        mcqIntv8.setCreator(student3);
        mcqIntv8.addOneTag(tagMcq);
        mcqIntv8.addOneTag(tagInterview);
        mcqIntv8.addOneTag(tagAgile);
        tagInterview.addOneQuestion(mcqIntv8);
        tagMcq.addOneQuestion(mcqIntv8);
        tagAgile.addOneQuestion(mcqIntv8);

        MultipleChoiceQuestion mcqIntv9 = new MultipleChoiceQuestion();
        mcqIntv9.setQuestionDetails("Who is responsible for sprint meeting?");
        MultipleChoiceOption mcoIntv9_1 = new MultipleChoiceOption("Sprint planning", false, mcqIntv9);
        MultipleChoiceOption mcoIntv9_2 = new MultipleChoiceOption("Product backlog", true, mcqIntv9);
        MultipleChoiceOption mcoIntv9_3 = new MultipleChoiceOption("Sprint retrospective",false, mcqIntv9);
        MultipleChoiceOption mcoIntv9_4 = new MultipleChoiceOption("Meetings",false, mcqIntv9);
        mcqIntv9.setCreator(student3);
        mcqIntv9.addOneTag(tagMcq);
        mcqIntv9.addOneTag(tagInterview);
        mcqIntv9.addOneTag(tagAgile);
        tagInterview.addOneQuestion(mcqIntv9);
        tagMcq.addOneQuestion(mcqIntv9);
        tagAgile.addOneQuestion(mcqIntv9);

        MultipleChoiceQuestion mcqIntv10 = new MultipleChoiceQuestion();
        mcqIntv10.setQuestionDetails("Which class is used to map a database row to a java object in spring?");
        MultipleChoiceOption mcoIntv10_1 = new MultipleChoiceOption("ResultSet", false, mcqIntv10);
        MultipleChoiceOption mcoIntv10_2 = new MultipleChoiceOption("RowMapper", true, mcqIntv10);
        MultipleChoiceOption mcoIntv10_3 = new MultipleChoiceOption("RowSetMapper",false, mcqIntv10);
        MultipleChoiceOption mcoIntv10_4 = new MultipleChoiceOption("ResultSetMapper",false, mcqIntv10);
        mcqIntv10.setCreator(student3);
        mcqIntv10.addOneTag(tagMcq);
        mcqIntv10.addOneTag(tagInterview);
        mcqIntv10.addOneTag(tagSpring);
        tagInterview.addOneQuestion(mcqIntv10);
        tagMcq.addOneQuestion(mcqIntv10);
        tagSpring.addOneQuestion(mcqIntv10);


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
        
        
        // Save interview questions
        mcqIntv1 = (MultipleChoiceQuestion) questionService.save(mcqIntv1);
        mcoService.save(mcoIntv1_1);
        mcoService.save(mcoIntv1_2);
        mcoService.save(mcoIntv1_3);
        mcoService.save(mcoIntv1_4);

        mcqIntv2 = (MultipleChoiceQuestion) questionService.save(mcqIntv2);
        mcoService.save(mcoIntv2_1);
        mcoService.save(mcoIntv2_2);
        mcoService.save(mcoIntv2_3);
        mcoService.save(mcoIntv2_4);
        
        mcqIntv3 = (MultipleChoiceQuestion) questionService.save(mcqIntv3);
        mcoService.save(mcoIntv3_1);
        mcoService.save(mcoIntv3_2);
        mcoService.save(mcoIntv3_3);
        mcoService.save(mcoIntv3_4);
        
        mcqIntv4 = (MultipleChoiceQuestion) questionService.save(mcqIntv4);
        mcoService.save(mcoIntv4_1);
        mcoService.save(mcoIntv4_2);
        mcoService.save(mcoIntv4_3);
        mcoService.save(mcoIntv4_4);
        
        mcqIntv5 = (MultipleChoiceQuestion) questionService.save(mcqIntv5);
        mcoService.save(mcoIntv5_1);
        mcoService.save(mcoIntv5_2);
        mcoService.save(mcoIntv5_3);
        mcoService.save(mcoIntv5_4);

        mcqIntv6 = (MultipleChoiceQuestion) questionService.save(mcqIntv6);
        mcoService.save(mcoIntv6_1);
        mcoService.save(mcoIntv6_2);
        mcoService.save(mcoIntv6_3);
        mcoService.save(mcoIntv6_4);
        
        mcqIntv7 = (MultipleChoiceQuestion) questionService.save(mcqIntv7);
        mcoService.save(mcoIntv7_1);
        mcoService.save(mcoIntv7_2);
        mcoService.save(mcoIntv7_3);
        mcoService.save(mcoIntv7_4);
        
        mcqIntv8 = (MultipleChoiceQuestion) questionService.save(mcqIntv8);
        mcoService.save(mcoIntv8_1);
        mcoService.save(mcoIntv8_2);
        mcoService.save(mcoIntv8_3);
        mcoService.save(mcoIntv8_4);
        
        mcqIntv9 = (MultipleChoiceQuestion) questionService.save(mcqIntv9);
        mcoService.save(mcoIntv9_1);
        mcoService.save(mcoIntv9_2);
        mcoService.save(mcoIntv9_3);
        mcoService.save(mcoIntv9_4);        
        
        
        mcqIntv10 = (MultipleChoiceQuestion) questionService.save(mcqIntv10);
        mcoService.save(mcoIntv10_1);
        mcoService.save(mcoIntv10_2);
        mcoService.save(mcoIntv10_3);
        mcoService.save(mcoIntv10_4);  
        
        
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
