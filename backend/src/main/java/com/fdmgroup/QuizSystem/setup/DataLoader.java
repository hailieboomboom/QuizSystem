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
    
    	// trainer - id 1
        Trainer trainer = new Trainer(); 
        trainer.setUsername("Jason");
        trainer.setPassword(passwordEncoder.encode("1"));
        trainer.setEmail("123@gmail.com");
        trainer.setFirstName("JHJ");
        trainer.setLastName("Liu");
        trainer.setRole(Role.AUTHORISED_TRAINER);
        trainerService.save(trainer);  
        System.out.println(trainerService.findByUsername("Jason"));

        // sales - id 2
        Sales sales = new Sales();
        sales.setUsername("Yutta");
        sales.setPassword(passwordEncoder.encode("1"));
        sales.setEmail("321@gmail.com");
        sales.setFirstName("Yutta");
        sales.setLastName("Karima");
        sales.setRole(Role.AUTHORISED_SALES);
        salesService.save(sales);  
        System.out.println(salesService.findByUsername("Yutta"));

        // unauthorisedTrainer - id 3
        Trainer unauthorisedTrainer = new Trainer();
        unauthorisedTrainer.setUsername("ut");
        unauthorisedTrainer.setPassword(passwordEncoder.encode("1"));
        unauthorisedTrainer.setEmail("1234@gmail.com");
        unauthorisedTrainer.setFirstName("JHJ");
        unauthorisedTrainer.setLastName("Liu");
        trainerService.save(unauthorisedTrainer); 
        System.out.println(trainerService.findByUsername("ut"));

        // unauthorisedTrainer1 - id 4
        Trainer unauthorisedTrainer1 = new Trainer();
        unauthorisedTrainer1.setUsername("ut1");
        unauthorisedTrainer1.setPassword(passwordEncoder.encode("1"));
        unauthorisedTrainer1.setEmail("12345@gmail.com");
        unauthorisedTrainer1.setFirstName("JHJ");
        unauthorisedTrainer1.setLastName("Liu");
        trainerService.save(unauthorisedTrainer1);  
        System.out.println(trainerService.findByUsername("ut1"));

        // unauthorisedTrainer1 - id 5
        Trainer unauthorisedTrainer2 = new Trainer();
        unauthorisedTrainer2.setUsername("ut2");
        unauthorisedTrainer2.setPassword(passwordEncoder.encode("1"));
        unauthorisedTrainer2.setEmail("123467@gmail.com");
        unauthorisedTrainer2.setFirstName("JHJ");
        unauthorisedTrainer2.setLastName("Liu");
        trainerService.save(unauthorisedTrainer2);  
        System.out.println(trainerService.findByUsername("ut2"));

        // unauthorisedSales - id 6
        Sales unauthorisedSales = new Sales();
        unauthorisedSales.setUsername("us");
        unauthorisedSales.setPassword(passwordEncoder.encode("1"));
        unauthorisedSales.setEmail("3210@gmail.com");
        unauthorisedSales.setFirstName("Yutta");
        unauthorisedSales.setLastName("Karima");
        salesService.save(unauthorisedSales);  
        System.out.println(salesService.findByUsername("us"));

        // trainingStudent - id 7
        Student student1 = new Student();
        student1.setUsername("skarima");
        student1.setPassword(passwordEncoder.encode("1"));
        student1.setEmail("student1@gmail.com");
        student1.setFirstName("student1");
        student1.setLastName("Karima");
        studentService.save(student1);

        // pondStudent - id 8
        Student student2 = new Student();
        student2.setUsername("slong");
        student2.setPassword(passwordEncoder.encode("2"));
        student2.setEmail("student2@gmail.com");
        student2.setFirstName("student2");
        student2.setLastName("Long");
        student2.setRole(Role.POND);
        studentService.save(student2);

        // beachedStudent - id 9
        Student student3 = new Student();
        student3.setUsername("sbrown");
        student3.setPassword(passwordEncoder.encode("3"));
        student3.setEmail("student3@gmail.com");
        student3.setFirstName("student3");
        student3.setLastName("Brown");
        student3.setRole(Role.BEACHED);
        studentService.save(student3);

        log.info("Finished loading users");


        ////////// Load Questions ////////////

        // MultipleChoice questions
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

        // ShortAnswer questions
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

        // Tags for questions
        Tag tagCourse = new Tag("course");
        Tag tagInterview = new Tag("interview");
        Tag tagJava = new Tag("java");
        Tag tagPython = new Tag("python");
        Tag tagSpringBoot = new Tag("springboot");
        Tag tagSql = new Tag("sql");
        Tag tagUnix = new Tag("unix");
        Tag tagOod = new Tag("ood");
        Tag tagJpa = new Tag("jpa");
        Tag tagSpring = new Tag("spring");
        Tag tagReact = new Tag("react");
        Tag tagJavascript = new Tag("javascript");
        Tag tagProskills = new Tag("proskills");
        Tag tagWeb = new Tag("web");
        Tag tagAgile = new Tag("agile");
        Tag tagOther = new Tag("other");
        Tag tagMcq = new Tag("mcq");

        
        tagCourse.addOneQuestion(mcq1);
        tagCourse.addOneQuestion(sa1);
        tagCourse.addOneQuestion(sa2);
        tagCourse.addOneQuestion(sa3);
        tagCourse.addOneQuestion(sa4);
        tagInterview.addOneQuestion(mcq2);
        tagInterview.addOneQuestion(sa1);
        tagInterview.addOneQuestion(sa4);
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
        mcqIntv9.setQuestionDetails("In Scrum, the prioritized work to be done is referred as");
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
        
        mcq4 = (MultipleChoiceQuestion) questionService.save(mcq4);
        mcoService.save(mco41);
        mcoService.save(mco42);
        mcoService.save(mco43);

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


        Quiz quiz1 = new Quiz();
        quiz1.setCreator(trainer);
        quiz1.setName("Course quiz created by trainer id 1"); // fixed the name to interview quiz to match with quiz type
        quiz1.setQuizCategory(QuizCategory.COURSE_QUIZ); // fixed the quiz type to match with question tags
        quiz1 = quizService.save(quiz1);
        quizService.addQuestionIntoQuiz(mcq1, quiz1, (float)5.0);
        quizService.addQuestionIntoQuiz(mcq3, quiz1, (float)6.0);
        
        Quiz quizCourse1 = new Quiz();
        quizCourse1.setCreator(trainer);
        quizCourse1.setName("Course Quiz 1 by student1 id 7");
        quizCourse1.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizCourse1 = quizService.save(quizCourse1);
        quizService.addQuestionIntoQuiz(mcq4, quizCourse1, (float) 5);
        quizService.addQuestionIntoQuiz(mcq6, quizCourse1, (float) 5);
        quizService.addQuestionIntoQuiz(mcq8, quizCourse1, (float) 5);
        quizService.addQuestionIntoQuiz(mcq5, quizCourse1, (float) 5);
        
        Quiz quizCourse2 = new Quiz();
        quizCourse2.setCreator(student2);
        quizCourse2.setName("Course Quiz 2 by student2 id 8");
        quizCourse2.setQuizCategory(QuizCategory.COURSE_QUIZ);
        quizCourse2 = quizService.save(quizCourse2);
        quizService.addQuestionIntoQuiz(mcq9, quizCourse2, (float) 5);
        quizService.addQuestionIntoQuiz(mcq7, quizCourse2, (float) 5);
        quizService.addQuestionIntoQuiz(mcq10, quizCourse2, (float) 5);
        quizService.addQuestionIntoQuiz(mcq12, quizCourse2, (float) 5);
        
        Quiz quizInterview1 = new Quiz();
        quizInterview1.setCreator(sales);
        quizInterview1.setName("Interview Quiz 1 by sales id 2");
        quizInterview1.setQuizCategory(QuizCategory.INTERVIEW_QUIZ);
        quizInterview1 = quizService.save(quizInterview1);
        quizService.addQuestionIntoQuiz(mcqIntv1, quizInterview1, (float) 5);
        quizService.addQuestionIntoQuiz(mcqIntv2, quizInterview1, (float) 5);
        quizService.addQuestionIntoQuiz(mcqIntv3, quizInterview1, (float) 5);
        quizService.addQuestionIntoQuiz(mcqIntv5, quizInterview1, (float) 5);
        
        Quiz quizInterview2 = new Quiz();
        quizInterview2.setCreator(student3);
        quizInterview2.setName("Interview Quiz 2 by student3 id 9");
        quizInterview2.setQuizCategory(QuizCategory.INTERVIEW_QUIZ);
        quizInterview2 = quizService.save(quizInterview2);
        quizService.addQuestionIntoQuiz(mcqIntv7, quizInterview2, (float) 5);
        quizService.addQuestionIntoQuiz(mcqIntv6, quizInterview2, (float) 5);
        quizService.addQuestionIntoQuiz(mcqIntv9, quizInterview2, (float) 5);
        quizService.addQuestionIntoQuiz(mcqIntv4, quizInterview2, (float) 5);

        System.out.println("--------SAVE QUIZ DONE-------");
        

        ////// quiz attempt //////

        QuizAttempt qa1 = new QuizAttempt();
        qa1.setQuiz(quiz1);
        qa1.setUser(student1);
        qa1.setAttemptNo(1);
        qa1.setTotalAwarded(0);
        qaRepo.save(qa1);
        
        QuizAttempt qa2 = new QuizAttempt();
        qa2.setQuiz(quiz1);
        qa2.setUser(student1);
        qa2.setAttemptNo(2);
        qa2.setTotalAwarded(5);
        qaRepo.save(qa2);

        QuizQuestionMCQAttemptKey mcqAttemptKey = new QuizQuestionMCQAttemptKey(qa1.getId(), mcq1.getId());
        QuizQuestionMCQAttempt mcqAttempt1 = new QuizQuestionMCQAttempt();
        mcqAttempt1.setKey(mcqAttemptKey);
        mcqAttempt1.setAwarded_grade(0);
        mcqAttempt1.setQuizAttempt(qa1);
        mcqAttempt1.setMultipleChoiceQuestion(mcq1);
        mcqAttempt1.setSelectedOption(mco1);
        mcqAttemptRepository.save(mcqAttempt1);
        
        QuizQuestionMCQAttemptKey mcqAttemptKey2 = new QuizQuestionMCQAttemptKey(qa2.getId(), mcq1.getId());
        QuizQuestionMCQAttempt mcqAttempt2 = new QuizQuestionMCQAttempt();
        mcqAttempt2.setKey(mcqAttemptKey2);
        mcqAttempt2.setAwarded_grade(5);
        mcqAttempt2.setQuizAttempt(qa2);
        mcqAttempt2.setMultipleChoiceQuestion(mcq1);
        mcqAttempt2.setSelectedOption(mco2);
        mcqAttemptRepository.save(mcqAttempt2);
        
        
        // QuizAttempt: student2 takes quizCourse1 twice
        QuizAttempt qa_qc1_student2_1 = new QuizAttempt();
        qa_qc1_student2_1.setQuiz(quizCourse1);
        qa_qc1_student2_1.setUser(student2);
        qa_qc1_student2_1.setAttemptNo(1);
        qa_qc1_student2_1.setTotalAwarded(0);
        qa_qc1_student2_1 = qaRepo.save(qa_qc1_student2_1);
        
        QuizQuestionMCQAttemptKey qaAttemptqc1MCQ4Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_1.getId(), mcq4.getId());
        QuizQuestionMCQAttempt qaAttemptqc1MCQ4 = new QuizQuestionMCQAttempt();
        qaAttemptqc1MCQ4.setKey(qaAttemptqc1MCQ4Key);
        qaAttemptqc1MCQ4.setQuizAttempt(qa_qc1_student2_1);
        qaAttemptqc1MCQ4.setMultipleChoiceQuestion(mcq4);
        qaAttemptqc1MCQ4.setSelectedOption(mco41);
        qaAttemptqc1MCQ4.setAwarded_grade(0);
        mcqAttemptRepository.save(qaAttemptqc1MCQ4);
        
        QuizQuestionMCQAttemptKey qaAttemptqc1MCQ5Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_1.getId(), mcq5.getId());
        QuizQuestionMCQAttempt qaAttemptqc1MCQ5 = new QuizQuestionMCQAttempt();
        qaAttemptqc1MCQ5.setKey(qaAttemptqc1MCQ5Key);
        qaAttemptqc1MCQ5.setQuizAttempt(qa_qc1_student2_1);
        qaAttemptqc1MCQ5.setMultipleChoiceQuestion(mcq5);
        qaAttemptqc1MCQ5.setSelectedOption(mco51);
        qaAttemptqc1MCQ5.setAwarded_grade(0);
        mcqAttemptRepository.save(qaAttemptqc1MCQ5);
        
        QuizQuestionMCQAttemptKey qaAttemptqc1MCQ6Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_1.getId(), mcq6.getId());
        QuizQuestionMCQAttempt qaAttemptqc1MCQ6 = new QuizQuestionMCQAttempt();
        qaAttemptqc1MCQ6.setKey(qaAttemptqc1MCQ6Key);
        qaAttemptqc1MCQ6.setQuizAttempt(qa_qc1_student2_1);
        qaAttemptqc1MCQ6.setMultipleChoiceQuestion(mcq6);
        qaAttemptqc1MCQ6.setSelectedOption(mco61);
        qaAttemptqc1MCQ6.setAwarded_grade(0);
        mcqAttemptRepository.save(qaAttemptqc1MCQ6);
        
        QuizQuestionMCQAttemptKey qaAttemptqc1MCQ8Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_1.getId(), mcq8.getId());
        QuizQuestionMCQAttempt qaAttemptqc1MCQ8 = new QuizQuestionMCQAttempt();
        qaAttemptqc1MCQ8.setKey(qaAttemptqc1MCQ8Key);
        qaAttemptqc1MCQ8.setQuizAttempt(qa_qc1_student2_1);
        qaAttemptqc1MCQ8.setMultipleChoiceQuestion(mcq8);
        qaAttemptqc1MCQ8.setSelectedOption(mco81);
        qaAttemptqc1MCQ8.setAwarded_grade(0);
        mcqAttemptRepository.save(qaAttemptqc1MCQ8);
        
        // student2 quizCourse1 attempt 2
        QuizAttempt qa_qc1_student2_2 = new QuizAttempt();
        qa_qc1_student2_2.setQuiz(quizCourse1);
        qa_qc1_student2_2.setUser(student2);
        qa_qc1_student2_2.setAttemptNo(2);
        qa_qc1_student2_2.setTotalAwarded(15);
        qa_qc1_student2_2 = qaRepo.save(qa_qc1_student2_2);
        
        QuizQuestionMCQAttemptKey qaAttempt2qc1MCQ4Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_2.getId(), mcq4.getId());
        QuizQuestionMCQAttempt qaAttempt2qc1MCQ4 = new QuizQuestionMCQAttempt();
        qaAttempt2qc1MCQ4.setKey(qaAttempt2qc1MCQ4Key);
        qaAttempt2qc1MCQ4.setQuizAttempt(qa_qc1_student2_2);
        qaAttempt2qc1MCQ4.setMultipleChoiceQuestion(mcq4);
        qaAttempt2qc1MCQ4.setSelectedOption(mco43);
        qaAttempt2qc1MCQ4.setAwarded_grade(5);
        mcqAttemptRepository.save(qaAttempt2qc1MCQ4);
        
        QuizQuestionMCQAttemptKey qaAttempt2qc1MCQ5Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_2.getId(), mcq5.getId());
        QuizQuestionMCQAttempt qaAttempt2qc1MCQ5 = new QuizQuestionMCQAttempt();
        qaAttempt2qc1MCQ5.setKey(qaAttempt2qc1MCQ5Key);
        qaAttempt2qc1MCQ5.setQuizAttempt(qa_qc1_student2_2);
        qaAttempt2qc1MCQ5.setMultipleChoiceQuestion(mcq5);
        qaAttempt2qc1MCQ5.setSelectedOption(mco53);
        qaAttempt2qc1MCQ5.setAwarded_grade(5);
        mcqAttemptRepository.save(qaAttempt2qc1MCQ5);
        
        QuizQuestionMCQAttemptKey qaAttempt2qc1MCQ6Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_2.getId(), mcq6.getId());
        QuizQuestionMCQAttempt qaAttempt2qc1MCQ6 = new QuizQuestionMCQAttempt();
        qaAttempt2qc1MCQ6.setKey(qaAttempt2qc1MCQ6Key);
        qaAttempt2qc1MCQ6.setQuizAttempt(qa_qc1_student2_2);
        qaAttempt2qc1MCQ6.setMultipleChoiceQuestion(mcq6);
        qaAttempt2qc1MCQ6.setSelectedOption(mco63);
        qaAttempt2qc1MCQ6.setAwarded_grade(5);
        mcqAttemptRepository.save(qaAttempt2qc1MCQ6);
        
        QuizQuestionMCQAttemptKey qaAttempt2qc1MCQ8Key = new QuizQuestionMCQAttemptKey(qa_qc1_student2_2.getId(), mcq8.getId());
        QuizQuestionMCQAttempt qaAttempt2qc1MCQ8 = new QuizQuestionMCQAttempt();
        qaAttempt2qc1MCQ8.setKey(qaAttempt2qc1MCQ8Key);
        qaAttempt2qc1MCQ8.setQuizAttempt(qa_qc1_student2_2);
        qaAttempt2qc1MCQ8.setMultipleChoiceQuestion(mcq8);
        qaAttempt2qc1MCQ8.setSelectedOption(mco82);
        qaAttempt2qc1MCQ8.setAwarded_grade(0);
        mcqAttemptRepository.save(qaAttempt2qc1MCQ8);

        // student3 quizCourse1 attempt 1
        QuizAttempt qa_qc1_student3_1 = new QuizAttempt();
        qa_qc1_student3_1.setQuiz(quizCourse1);
        qa_qc1_student3_1.setUser(student3);
        qa_qc1_student3_1.setAttemptNo(1);
        qa_qc1_student3_1.setTotalAwarded(20);
        qa_qc1_student3_1 = qaRepo.save(qa_qc1_student3_1);

        QuizQuestionMCQAttemptKey qaAttempt1qc1MCQ4Key = new QuizQuestionMCQAttemptKey(qa_qc1_student3_1.getId(), mcq4.getId());
        QuizQuestionMCQAttempt qaAttempt1qc1MCQ4 = new QuizQuestionMCQAttempt();
        qaAttempt1qc1MCQ4.setKey(qaAttempt1qc1MCQ4Key);
        qaAttempt1qc1MCQ4.setQuizAttempt(qa_qc1_student3_1);
        qaAttempt1qc1MCQ4.setMultipleChoiceQuestion(mcq4);
        qaAttempt1qc1MCQ4.setSelectedOption(mco43);
        qaAttempt1qc1MCQ4.setAwarded_grade(5);
        mcqAttemptRepository.save(qaAttempt1qc1MCQ4);
        
        QuizQuestionMCQAttemptKey qaAttempt1qc1MCQ5Key = new QuizQuestionMCQAttemptKey(qa_qc1_student3_1.getId(), mcq5.getId());
        QuizQuestionMCQAttempt qaAttempt1qc1MCQ5 = new QuizQuestionMCQAttempt();
        qaAttempt1qc1MCQ5.setKey(qaAttempt1qc1MCQ5Key);
        qaAttempt1qc1MCQ5.setQuizAttempt(qa_qc1_student3_1);
        qaAttempt1qc1MCQ5.setMultipleChoiceQuestion(mcq5);
        qaAttempt1qc1MCQ5.setSelectedOption(mco53);
        qaAttempt1qc1MCQ5.setAwarded_grade(5);
        mcqAttemptRepository.save(qaAttempt1qc1MCQ5);
        
        QuizQuestionMCQAttemptKey qaAttempt1qc1MCQ6Key = new QuizQuestionMCQAttemptKey(qa_qc1_student3_1.getId(), mcq6.getId());
        QuizQuestionMCQAttempt qaAttempt1qc1MCQ6 = new QuizQuestionMCQAttempt();
        qaAttempt1qc1MCQ6.setKey(qaAttempt1qc1MCQ6Key);
        qaAttempt1qc1MCQ6.setQuizAttempt(qa_qc1_student3_1);
        qaAttempt1qc1MCQ6.setMultipleChoiceQuestion(mcq6);
        qaAttempt1qc1MCQ6.setSelectedOption(mco63);
        qaAttempt1qc1MCQ6.setAwarded_grade(5);
        mcqAttemptRepository.save(qaAttempt1qc1MCQ6);
        
        QuizQuestionMCQAttemptKey qaAttempt1qc1MCQ8Key = new QuizQuestionMCQAttemptKey(qa_qc1_student3_1.getId(), mcq8.getId());
        QuizQuestionMCQAttempt qaAttempt1qc1MCQ8 = new QuizQuestionMCQAttempt();
        qaAttempt1qc1MCQ8.setKey(qaAttempt1qc1MCQ8Key);
        qaAttempt1qc1MCQ8.setQuizAttempt(qa_qc1_student3_1);
        qaAttempt1qc1MCQ8.setMultipleChoiceQuestion(mcq8);
        qaAttempt1qc1MCQ8.setSelectedOption(mco83);
        qaAttempt1qc1MCQ8.setAwarded_grade(5);
        mcqAttemptRepository.save(qaAttempt1qc1MCQ8);

        // -------- Below is added by Summer --------
        
        // student1 quizCourse1 attempt 1  
        QuizAttempt quizCrse1_stu1_attempt1 = new QuizAttempt();
        quizCrse1_stu1_attempt1.setQuiz(quizCourse1);
        quizCrse1_stu1_attempt1.setUser(student1);
        quizCrse1_stu1_attempt1.setAttemptNo(1);
        quizCrse1_stu1_attempt1.setTotalAwarded(20);
        quizCrse1_stu1_attempt1 = qaRepo.save(quizCrse1_stu1_attempt1);
        
        QuizQuestionMCQAttemptKey mcq4_stu2_attempt1Key = new QuizQuestionMCQAttemptKey(quizCrse1_stu1_attempt1.getId(), mcq4.getId());
        QuizQuestionMCQAttempt mcq4_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcq4_stu2_attempt1.setKey(mcq4_stu2_attempt1Key);
        mcq4_stu2_attempt1.setQuizAttempt(quizCrse1_stu1_attempt1);
        mcq4_stu2_attempt1.setMultipleChoiceQuestion(mcq4);
        mcq4_stu2_attempt1.setSelectedOption(mco43);
        mcq4_stu2_attempt1.setAwarded_grade(5);
        mcqAttemptRepository.save(mcq4_stu2_attempt1);
        
        QuizQuestionMCQAttemptKey mcq5_stu2_attempt1Key = new QuizQuestionMCQAttemptKey(quizCrse1_stu1_attempt1.getId(), mcq5.getId());
        QuizQuestionMCQAttempt mcq5_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcq5_stu2_attempt1.setKey(mcq5_stu2_attempt1Key);
        mcq5_stu2_attempt1.setQuizAttempt(quizCrse1_stu1_attempt1);
        mcq5_stu2_attempt1.setMultipleChoiceQuestion(mcq5);
        mcq5_stu2_attempt1.setSelectedOption(mco53);
        mcq5_stu2_attempt1.setAwarded_grade(5);
        mcqAttemptRepository.save(mcq5_stu2_attempt1);
        
        QuizQuestionMCQAttemptKey mcq6_stu2_attempt1Key = new QuizQuestionMCQAttemptKey(quizCrse1_stu1_attempt1.getId(), mcq6.getId());
        QuizQuestionMCQAttempt mcq6_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcq6_stu2_attempt1.setKey(mcq6_stu2_attempt1Key);
        mcq6_stu2_attempt1.setQuizAttempt(quizCrse1_stu1_attempt1);
        mcq6_stu2_attempt1.setMultipleChoiceQuestion(mcq6);
        mcq6_stu2_attempt1.setSelectedOption(mco63);
        mcq6_stu2_attempt1.setAwarded_grade(5);
        mcqAttemptRepository.save(mcq6_stu2_attempt1);
        
        QuizQuestionMCQAttemptKey mcq8_stu2_attempt1Key = new QuizQuestionMCQAttemptKey(quizCrse1_stu1_attempt1.getId(), mcq8.getId());
        QuizQuestionMCQAttempt mcq8_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcq8_stu2_attempt1.setKey(mcq8_stu2_attempt1Key);
        mcq8_stu2_attempt1.setQuizAttempt(quizCrse1_stu1_attempt1);
        mcq8_stu2_attempt1.setMultipleChoiceQuestion(mcq8);
        mcq8_stu2_attempt1.setSelectedOption(mco83);
        mcq8_stu2_attempt1.setAwarded_grade(5);
        mcqAttemptRepository.save(mcq8_stu2_attempt1);

        ////// Interview Quiz Attempt /////
        
        // student2 (pond student) attempted quizInterview1 twice 
        QuizAttempt quizIntv1_stu2_attempt1 = new QuizAttempt();
        quizIntv1_stu2_attempt1.setQuiz(quizInterview1);
        quizIntv1_stu2_attempt1.setUser(student2);
        quizIntv1_stu2_attempt1.setAttemptNo(1);
        quizIntv1_stu2_attempt1.setTotalAwarded(10);
        qaRepo.save(quizIntv1_stu2_attempt1);
           
        QuizQuestionMCQAttemptKey mcqIntv1_stu2_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt1.getId(), mcqIntv1.getId());
        QuizQuestionMCQAttempt mcqIntv1_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcqIntv1_stu2_attempt1.setKey(mcqIntv1_stu2_attempt1_key);
        mcqIntv1_stu2_attempt1.setAwarded_grade(5);
        mcqIntv1_stu2_attempt1.setQuizAttempt(quizIntv1_stu2_attempt1);
        mcqIntv1_stu2_attempt1.setMultipleChoiceQuestion(mcqIntv1);
        mcqIntv1_stu2_attempt1.setSelectedOption(mcoIntv1_2); 
        mcqAttemptRepository.save(mcqIntv1_stu2_attempt1);
        
        QuizQuestionMCQAttemptKey mcqIntv2_stu2_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt1.getId(), mcqIntv2.getId());
        QuizQuestionMCQAttempt mcqIntv2_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcqIntv2_stu2_attempt1.setKey(mcqIntv2_stu2_attempt1_key);
        mcqIntv2_stu2_attempt1.setAwarded_grade(0);
        mcqIntv2_stu2_attempt1.setQuizAttempt(quizIntv1_stu2_attempt1);
        mcqIntv2_stu2_attempt1.setMultipleChoiceQuestion(mcqIntv2);
        mcqIntv2_stu2_attempt1.setSelectedOption(mcoIntv2_2); 
        mcqAttemptRepository.save(mcqIntv2_stu2_attempt1);

        QuizQuestionMCQAttemptKey mcqIntv3_stu2_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt1.getId(), mcqIntv3.getId());
        QuizQuestionMCQAttempt mcqIntv3_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcqIntv3_stu2_attempt1.setKey(mcqIntv3_stu2_attempt1_key);
        mcqIntv3_stu2_attempt1.setAwarded_grade(5);
        mcqIntv3_stu2_attempt1.setQuizAttempt(quizIntv1_stu2_attempt1);
        mcqIntv3_stu2_attempt1.setMultipleChoiceQuestion(mcqIntv3);
        mcqIntv3_stu2_attempt1.setSelectedOption(mcoIntv3_2); 
        mcqAttemptRepository.save(mcqIntv3_stu2_attempt1);

        QuizQuestionMCQAttemptKey mcqIntv5_stu2_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt1.getId(), mcqIntv5.getId());
        QuizQuestionMCQAttempt mcqIntv5_stu2_attempt1 = new QuizQuestionMCQAttempt();
        mcqIntv5_stu2_attempt1.setKey(mcqIntv5_stu2_attempt1_key);
        mcqIntv5_stu2_attempt1.setAwarded_grade(0);
        mcqIntv5_stu2_attempt1.setQuizAttempt(quizIntv1_stu2_attempt1);
        mcqIntv5_stu2_attempt1.setMultipleChoiceQuestion(mcqIntv5);
        mcqIntv5_stu2_attempt1.setSelectedOption(mcoIntv5_1); 
        mcqAttemptRepository.save(mcqIntv5_stu2_attempt1);
        
        // student2 quizInterview1 attempt 2
        QuizAttempt quizIntv1_stu2_attempt2 = new QuizAttempt();
        quizIntv1_stu2_attempt2.setQuiz(quizInterview1);
        quizIntv1_stu2_attempt2.setUser(student2);
        quizIntv1_stu2_attempt2.setAttemptNo(2);
        quizIntv1_stu2_attempt2.setTotalAwarded(20);
        qaRepo.save(quizIntv1_stu2_attempt2);
             
        QuizQuestionMCQAttemptKey mcqIntv1_stu2_attempt2_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt2.getId(), mcqIntv1.getId());
        QuizQuestionMCQAttempt mcqIntv1_stu2_attempt2 = new QuizQuestionMCQAttempt();
        mcqIntv1_stu2_attempt2.setKey(mcqIntv1_stu2_attempt2_key);
        mcqIntv1_stu2_attempt2.setAwarded_grade(5);
        mcqIntv1_stu2_attempt2.setQuizAttempt(quizIntv1_stu2_attempt2);
        mcqIntv1_stu2_attempt2.setMultipleChoiceQuestion(mcqIntv1);
        mcqIntv1_stu2_attempt2.setSelectedOption(mcoIntv1_2); 
        mcqAttemptRepository.save(mcqIntv1_stu2_attempt2);
        
        QuizQuestionMCQAttemptKey mcqIntv2_stu2_attempt2_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt2.getId(), mcqIntv2.getId());
        QuizQuestionMCQAttempt mcqIntv2_stu2_attempt2 = new QuizQuestionMCQAttempt();
        mcqIntv2_stu2_attempt2.setKey(mcqIntv2_stu2_attempt2_key);
        mcqIntv2_stu2_attempt2.setAwarded_grade(5);
        mcqIntv2_stu2_attempt2.setQuizAttempt(quizIntv1_stu2_attempt2);
        mcqIntv2_stu2_attempt2.setMultipleChoiceQuestion(mcqIntv2);
        mcqIntv2_stu2_attempt2.setSelectedOption(mcoIntv2_1); 
        mcqAttemptRepository.save(mcqIntv2_stu2_attempt2);
        
        QuizQuestionMCQAttemptKey mcqIntv3_stu2_attempt2_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt2.getId(), mcqIntv3.getId());
        QuizQuestionMCQAttempt mcqIntv3_stu2_attempt2 = new QuizQuestionMCQAttempt();
        mcqIntv3_stu2_attempt2.setKey(mcqIntv3_stu2_attempt2_key);
        mcqIntv3_stu2_attempt2.setAwarded_grade(5);
        mcqIntv3_stu2_attempt2.setQuizAttempt(quizIntv1_stu2_attempt2);
        mcqIntv3_stu2_attempt2.setMultipleChoiceQuestion(mcqIntv3);
        mcqIntv3_stu2_attempt2.setSelectedOption(mcoIntv3_2); 
        mcqAttemptRepository.save(mcqIntv3_stu2_attempt2);
        
        QuizQuestionMCQAttemptKey mcqIntv5_stu2_attempt2_key = new QuizQuestionMCQAttemptKey(quizIntv1_stu2_attempt2.getId(), mcqIntv5.getId());
        QuizQuestionMCQAttempt mcqIntv5_stu2_attempt2 = new QuizQuestionMCQAttempt();
        mcqIntv5_stu2_attempt2.setKey(mcqIntv5_stu2_attempt2_key);
        mcqIntv5_stu2_attempt2.setAwarded_grade(5);
        mcqIntv5_stu2_attempt2.setQuizAttempt(quizIntv1_stu2_attempt2);
        mcqIntv5_stu2_attempt2.setMultipleChoiceQuestion(mcqIntv5);
        mcqIntv5_stu2_attempt2.setSelectedOption(mcoIntv5_4); 
        mcqAttemptRepository.save(mcqIntv5_stu2_attempt2);
        
        
        // student2 quizInterview2 attempt 1    
        QuizAttempt quizIntv2_stu3_attempt1 = new QuizAttempt();
        quizIntv2_stu3_attempt1.setQuiz(quizInterview2);
        quizIntv2_stu3_attempt1.setUser(student3);
        quizIntv2_stu3_attempt1.setAttemptNo(1);
        quizIntv2_stu3_attempt1.setTotalAwarded(15);
        qaRepo.save(quizIntv2_stu3_attempt1);
           
        QuizQuestionMCQAttemptKey mcqIntv7_stu3_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv2_stu3_attempt1.getId(), mcqIntv7.getId());
        QuizQuestionMCQAttempt mcqIntv7_stu3_attempt1 = new QuizQuestionMCQAttempt();
        mcqIntv7_stu3_attempt1.setKey(mcqIntv7_stu3_attempt1_key);
        mcqIntv7_stu3_attempt1.setAwarded_grade(5);
        mcqIntv7_stu3_attempt1.setQuizAttempt(quizIntv2_stu3_attempt1);
        mcqIntv7_stu3_attempt1.setMultipleChoiceQuestion(mcqIntv7);
        mcqIntv7_stu3_attempt1.setSelectedOption(mcoIntv7_3); 
        mcqAttemptRepository.save(mcqIntv7_stu3_attempt1);
        
        QuizQuestionMCQAttemptKey mcqIntv6_stu3_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv2_stu3_attempt1.getId(), mcqIntv6.getId());
        QuizQuestionMCQAttempt mcqIntv6_stu3_attempt1 = new QuizQuestionMCQAttempt();
        mcqIntv6_stu3_attempt1.setKey(mcqIntv6_stu3_attempt1_key);
        mcqIntv6_stu3_attempt1.setAwarded_grade(5);
        mcqIntv6_stu3_attempt1.setQuizAttempt(quizIntv2_stu3_attempt1);
        mcqIntv6_stu3_attempt1.setMultipleChoiceQuestion(mcqIntv6);
        mcqIntv6_stu3_attempt1.setSelectedOption(mcoIntv6_3); 
        mcqAttemptRepository.save(mcqIntv6_stu3_attempt1);
        
        QuizQuestionMCQAttemptKey mcqIntv9_stu3_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv2_stu3_attempt1.getId(), mcqIntv9.getId());
        QuizQuestionMCQAttempt mcqIntv9_stu3_attempt1 = new QuizQuestionMCQAttempt();
        mcqIntv9_stu3_attempt1.setKey(mcqIntv9_stu3_attempt1_key);
        mcqIntv9_stu3_attempt1.setAwarded_grade(5);
        mcqIntv9_stu3_attempt1.setQuizAttempt(quizIntv2_stu3_attempt1);
        mcqIntv9_stu3_attempt1.setMultipleChoiceQuestion(mcqIntv9);
        mcqIntv9_stu3_attempt1.setSelectedOption(mcoIntv9_2); 
        mcqAttemptRepository.save(mcqIntv9_stu3_attempt1);
        
		 QuizQuestionMCQAttemptKey mcqIntv4_stu3_attempt1_key = new QuizQuestionMCQAttemptKey(quizIntv2_stu3_attempt1.getId(), mcqIntv4.getId());
		 QuizQuestionMCQAttempt mcqIntv4_stu3_attempt1 = new QuizQuestionMCQAttempt();
		 mcqIntv4_stu3_attempt1.setKey(mcqIntv4_stu3_attempt1_key);
		 mcqIntv4_stu3_attempt1.setAwarded_grade(0);
		 mcqIntv4_stu3_attempt1.setQuizAttempt(quizIntv2_stu3_attempt1);
		 mcqIntv4_stu3_attempt1.setMultipleChoiceQuestion(mcqIntv4);
		 mcqIntv4_stu3_attempt1.setSelectedOption(mcoIntv4_2); 
		 mcqAttemptRepository.save(mcqIntv4_stu3_attempt1);
	     
	     
        log.info("Finished setup");
        log.info("http://localhost:8088/QuizSystem");

    }
}
