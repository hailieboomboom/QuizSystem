package com.fdmgroup.QuizSystem.repository;

import com.fdmgroup.QuizSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Student repository.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByUsername(String username);

}
