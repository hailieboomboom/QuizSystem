package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.UnauthorisedSales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnauthorisedSalesRepository extends JpaRepository<UnauthorisedSales, Long> {
}
