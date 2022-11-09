package com.fdmgroup.QuizSystem.setup;
import javax.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import com.fdmgroup.QuizSystem.service.QuestionService;
import com.fdmgroup.QuizSystem.service.QuizService;
@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;
    private Log log = LogFactory.getLog(DataLoader.class);
    @Override
    @Transactional
    @Modifying
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting setup");
        ////////// Load Users ////////////
        ////////// Load Questions ////////////
        ////////// Load Quizzes ////////////
        log.info("Finished setup");
        log.info("http://localhost:8088/QuizSystem");
    }
}
