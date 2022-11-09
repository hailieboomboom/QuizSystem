package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesService {

    private final SalesRepository salesRepository;

    public Sales getSalesById(long id) {
        Optional<Sales> maybeSales = salesRepository.findById(id);
        if(maybeSales.isEmpty()) {
            throw new UserNotFoundException("Sales member cannot be found!");
        }
        return maybeSales.get();
    }
    
    public Sales findByUsername(String username) {
    	Optional<Sales> maybeSales = salesRepository.findByUsername(username);
    	if (maybeSales.isEmpty()) {
    		throw new UserNotFoundException("Sales user is not found");
    	}
    	return maybeSales.get();
    }
    
    public Sales update(Sales modifiedSales) {
    	Sales sales = findByUsername(modifiedSales.getUsername());
    	modifiedSales.setId(sales.getId());
    	return salesRepository.save(modifiedSales);
    }
    
    public Sales save(Sales sales) {
		return salesRepository.save(sales);
	}
}