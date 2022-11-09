package com.fdmgroup.QuizSystem.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.service.UnauthorisedSalesService;

@RestController
@RequestMapping("/sales")
public class SalesAuthorisationController {
	
	private UnauthorisedSalesService unauthorisedSalesService;
	
	@PostMapping("/authorise/{username}")
	public Sales authoriseSales(@PathVariable String username) {
		return unauthorisedSalesService.authoriseSales(username);
	}
}
