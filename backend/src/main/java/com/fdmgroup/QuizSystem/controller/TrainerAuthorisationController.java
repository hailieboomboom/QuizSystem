package com.fdmgroup.QuizSystem.controller;

import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.service.UnauthorisedTrainerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainer")
public class TrainerAuthorisationController {

    private UnauthorisedTrainerService unauthorisedTrainerService;

    @PostMapping("/authorise/{username}")
    public Trainer authoriseTrainer(@PathVariable String username){
    	return unauthorisedTrainerService.authoriseTrainer(username);
    }
}
