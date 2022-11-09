package com.fdmgroup.QuizSystem.controller;

import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.service.TrainerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainer")
public class TrainerAuthorisationController {

    private TrainerService trainerService;

    @PostMapping("/authorise/{username}")
    public Trainer authoriseTrainer(@PathVariable String username){

    	return trainerService.authoriseTrainer(username);
    }
}
