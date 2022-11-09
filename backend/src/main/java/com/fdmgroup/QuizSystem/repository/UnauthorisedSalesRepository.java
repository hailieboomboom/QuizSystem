package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.UnauthorisedSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnauthorisedSalesRepository extends JpaRepository<UnauthorisedSales, Long> {
    Optional<UnauthorisedSales> findByUsername(String username);
}
