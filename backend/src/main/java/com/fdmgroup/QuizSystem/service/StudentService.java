package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Student service is used to retrieve student object and change student categories.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    /**
     * Get student object by user id.
     * @param id User id.
     * @return   Student.
     */
    public Student findStudentById(long id) {
        Optional<Student> maybeStudent = studentRepository.findById(id);
        if (maybeStudent.isEmpty()) {
            throw new UserNotFoundException("Student is not found!");
        }
        return maybeStudent.get();
    }

    /**
     * Update student category.
     * @param username Username.
     * @param role     Role to be updated.
     * @return         Student.
     */
    public Student updateCategory(String username, Role role) {
        Optional<Student> maybeStudent = studentRepository.findStudentByUsername(username);
        if (maybeStudent.isEmpty()) {
            throw new UserNotFoundException("Student is not found!");
        }
        Student student = maybeStudent.get();
        student.setRole(role);
        return studentRepository.save(student);
    }

    /**
     * Get all students.
     * @return A list of students.
     */
	public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    /**
     * Persist student into database.
     * @param student Student.
     * @return        Persisted student.
     */
    public Student save(Student student){
        return studentRepository.save(student);
    }
}
