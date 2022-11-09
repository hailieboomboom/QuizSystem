package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long> {

    Optional<Sales> findByUsername(String username);
}
