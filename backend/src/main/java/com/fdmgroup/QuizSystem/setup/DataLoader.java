package com.fdmgroup.QuizSystem.setup;

import javax.transaction.Transactional;

import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.model.UnauthorisedSales;
import com.fdmgroup.QuizSystem.repository.UnauthorisedSalesRepository;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.TrainerService;
import com.fdmgroup.QuizSystem.service.UnauthorisedSalesService;

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
    private UnauthorisedSalesService unauthorisedSalesService;
    private Log log = LogFactory.getLog(DataLoader.class);
    
    
    @Autowired
    public DataLoader(TrainerService trainerService, SalesService salesService, UnauthorisedSalesService unauthorisedSalesService) {
        super();
        this.trainerService = trainerService;
        this.salesService = salesService;
        this.unauthorisedSalesService = unauthorisedSalesService;

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
        System.out.println(trainerService.findByUsername("Jason"));
        
        Sales sales = new Sales();
        sales.setUsername("Yutta");
        sales.setPassword("321");
        sales.setEmail("321@gmail.com");
        sales.setFirstName("Yutta");
        sales.setLastName("Karima");
        salesService.save(sales);
        System.out.println(salesService.findByUsername("Yutta"));
        
        UnauthorisedSales unauthorisedSales = new UnauthorisedSales();
        unauthorisedSales.setUsername("Chris");
        unauthorisedSales.setPassword("321");
        unauthorisedSales.setEmail("321@gmail.com");
        unauthorisedSales.setFirstName("Chris");
        unauthorisedSales.setLastName("Tang");
        unauthorisedSalesService.save(unauthorisedSales);
        System.out.println(unauthorisedSalesService.findByUsername("Chris"));
        
        log.info("Finished setup");
    }
}
