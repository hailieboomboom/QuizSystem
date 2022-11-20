package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Sales;
import com.fdmgroup.QuizSystem.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Sales service.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SalesService {

    private final SalesRepository salesRepository;

    /**
     * Get sales by id.
     * @param id User id.
     * @return   Sales.
     */
    public Sales getSalesById(long id) {
        Optional<Sales> maybeSales = salesRepository.findById(id);
        if(maybeSales.isEmpty()) {
            throw new UserNotFoundException("Sales member cannot be found!");
        }
        return maybeSales.get();
    }

    /**
     * Get sales by username.
     * @param username Username.
     * @return         Sales.
     */
    public Sales findByUsername(String username) {
    	Optional<Sales> maybeSales = salesRepository.findByUsername(username);
    	if (maybeSales.isEmpty()) {
    		throw new UserNotFoundException("Sales user is not found");
    	}
    	return maybeSales.get();
    }

    /**
     * Get all unauthorised sales.
     * @return A list of unauthorised sales.
     */
    public List<Sales> getAllUnauthorisedSales(){
        return salesRepository.findAll().stream().filter(sales -> sales.getRole() == Role.UNAUTHORISED_SALES).toList();
    }

    /**
     * Authorise sales.
     * @param username Username.
     * @return         Sales.
     */
    public Sales authoriseSales(String username){
        Sales sales = findByUsername(username);
        sales.setRole(Role.AUTHORISED_SALES);
        return salesRepository.save(sales);
    }

    /**
     * Persist sales.
     * @param sales Sales.
     * @return      Persisted sales.
     */
    public Sales save(Sales sales) {
		return salesRepository.save(sales);
	}
}
