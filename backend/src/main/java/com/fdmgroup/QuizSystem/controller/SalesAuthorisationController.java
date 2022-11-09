package com.fdmgroup.QuizSystem.controller;

import com.fdmgroup.QuizSystem.service.SalesService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.QuizSystem.model.Sales;

@RestController
@RequestMapping("/sales")
public class SalesAuthorisationController {
	
	private SalesService salesService;

	@PostMapping("/authorise/{username}")
	public Sales authoriseSales(@PathVariable String username) {
		return salesService.authoriseSales(username);
	}
}
