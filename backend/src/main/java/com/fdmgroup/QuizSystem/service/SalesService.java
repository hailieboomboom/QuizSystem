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

    public Sales updateSales(long id, UserUpdateDTO modifiedSales) {
        Optional<Sales> maybeSales = salesRepository.findById(id);
        if(maybeSales.isEmpty()){
            throw new UserNotFoundException();
        }
        if (salesRepository.existsByUsername(modifiedSales.getUsername()) && ! maybeSales.get().getUsername().equals(modifiedSales.getUsername())) {
            throw new UserAlreadyExistsException(String.format("Username %s already been used", modifiedSales.getUsername()));
        }

        if (salesRepository.existsByEmail(modifiedSales.getEmail()) && ! maybeSales.get().getEmail().equals(modifiedSales.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email %s already been used", modifiedSales.getEmail()));
        }
        // Update user with new attributes
        Sales sales = maybeSales.get();
        sales.setUsername(modifiedSales.getUsername());
        sales.setPassword(modifiedSales.getPassword());
        sales.setEmail(modifiedSales.getEmail());
        sales.setFirstName(modifiedSales.getFirstName());
        sales.setLastName(modifiedSales.getLastName());
        // Role?
        return salesRepository.save(sales);
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
