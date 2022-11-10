package com.fdmgroup.QuizSystem.setup;
import javax.transaction.Transactional;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.service.SalesService;
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
    private SalesService salesService;
    private Log log = LogFactory.getLog(DataLoader.class);
    @Autowired
    public DataLoader(TrainerService trainerService, SalesService salesService) {
        super();
        this.trainerService = trainerService;
        this.salesService = salesService;

    }
    @Override
    @Transactional
    @Modifying
    public void run(ApplicationArguments args) throws Exception {
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
    }
}
