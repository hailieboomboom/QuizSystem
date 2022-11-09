package com.fdmgroup.QuizSystem.setup;

import javax.transaction.Transactional;

import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.service.TrainerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
@Component
public class DataLoader implements ApplicationRunner {
    private TrainerService trainerService;
    private Log log = LogFactory.getLog(DataLoader.class);
    @Autowired
    public DataLoader(TrainerService trainerService) {
        super();
        this.trainerService = trainerService;

    }
    @Override
    @Transactional
    @Modifying
    public void run(ApplicationArguments args) throws Exception {
        Trainer trainer = new Trainer();
        trainer.setUsername("Jason");
        trainer.setPassword("123");
        trainer.setEmail("123@gmail.com");
        trainer.setFirstName("JHJ");
        trainer.setLastName("Liu");
        trainerService.save(trainer);
        System.out.println(trainerService.findTrainerByUsername("Jason"));
        log.info("Finished setup");
    }
}
