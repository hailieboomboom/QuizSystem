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

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public Student findStudentByUsername(String username) {
        Optional<Student> maybeStudent = studentRepository.findStudentByUsername(username);
        if (maybeStudent.isEmpty()) {
            throw new UserNotFoundException("Student is not found!");
        }
        return maybeStudent.get();
    }
    public Student findStudentById(long id) {
        Optional<Student> maybeStudent = studentRepository.findById(id);
        if (maybeStudent.isEmpty()) {
            throw new UserNotFoundException("Student is not found!");
        }
        return maybeStudent.get();
    }

    public Student updateCategory(String username, Role role) {
        Optional<Student> maybeStudent = studentRepository.findStudentByUsername(username);
        if (maybeStudent.isEmpty()) {
            throw new UserNotFoundException("Student is not found!");
        }
        Student student = maybeStudent.get();
        //TODO: role should be within ("absent", "training", "POND", "BEACHED")
        student.setRole(role);
        return studentRepository.save(student);
    }

	public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student save(Student student){
        return studentRepository.save(student);
    }


}
