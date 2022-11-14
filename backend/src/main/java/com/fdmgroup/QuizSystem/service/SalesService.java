package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.dto.UserUpdateDTO;
import com.fdmgroup.QuizSystem.exception.UserAlreadyExistsException;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.model.Trainer;
import com.fdmgroup.QuizSystem.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Sales> getAllUnauthorisedSales(){
        return salesRepository.findAll().stream().filter(sales -> sales.getRole() == Role.UNAUTHORISED_SALES).toList();
    }

    public List<Sales> getAllSales(){
        return salesRepository.findAll();
    }

    public Sales authoriseSales(String username){
        Sales sales = findByUsername(username);
        sales.setRole(Role.AUTHORISED_SALES);
        return salesRepository.save(sales);
    }
    
    public Sales save(Sales sales) {
		return salesRepository.save(sales);
	}
}
