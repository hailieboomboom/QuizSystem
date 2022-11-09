package com.fdmgroup.QuizSystem.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.UnauthorisedSales;
import com.fdmgroup.QuizSystem.repository.SalesRepository;
import com.fdmgroup.QuizSystem.repository.UnauthorisedSalesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UnauthorisedSalesService {
	private final UnauthorisedSalesRepository unauthorisedSalesRepository;
	
	private final SalesRepository salesRepository;
	
	public UnauthorisedSales findByUsername(String username) {
        Optional<UnauthorisedSales> maybeSales = unauthorisedSalesRepository.findByUsername(username);
        if (maybeSales.isEmpty()) {
            throw new UserNotFoundException("Sales user is not found!");
        }
        return maybeSales.get();
    }
	
	public UnauthorisedSales save(UnauthorisedSales unauthorisedSales) {
		return unauthorisedSalesRepository.save(unauthorisedSales);
	}
	
	public void delete(String username) {
		UnauthorisedSales unauthorisedSales = findByUsername(username);
		unauthorisedSalesRepository.delete(unauthorisedSales);
	}
	
	public Sales authoriseSales(String username) {
		UnauthorisedSales unauthorisedSales = findByUsername(username);
		Sales sales = new Sales();
		sales.setUsername(unauthorisedSales.getUsername());
		sales.setEmail(unauthorisedSales.getEmail());
		sales.setPassword(unauthorisedSales.getPassword());
		sales.setFirstName(unauthorisedSales.getFirstName());
		sales.setLastName(unauthorisedSales.getLastName());
		unauthorisedSalesRepository.delete(unauthorisedSales);
		return salesRepository.save(sales);
	}
}
