package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository mockStudentRepo;

    @Mock
    private Student mockStudent;

    @BeforeEach
    void setup(){
        studentService = new StudentService(mockStudentRepo);
    }

    @Test
    void testFindStudentById(){
        long id = 1;
        when(mockStudentRepo.findById(id)).thenReturn(Optional.of(mockStudent));

        assertEquals(mockStudent, studentService.findStudentById(id));
    }

    @Test
    void testFindStudentById_ThrowUserNotFoundException(){
        long id = 1;
        when(mockStudentRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> studentService.findStudentById(id));
    }

    @Test
    void testUpdateCategory(){
        String username = "test";
        Role role = Role.POND;
        Student student = new Student();
        when(mockStudentRepo.findStudentByUsername(username)).thenReturn(Optional.of(student));
        when(mockStudentRepo.save(student)).thenReturn(student);

        assertEquals(Role.POND, studentService.updateCategory(username, role).getRole());
    }

    @Test
    void testUpdateCategory_ThrowUserNotFoundException(){
        String username = "test";
        Role role = Role.POND;
        when(mockStudentRepo.findStudentByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> studentService.updateCategory(username, role));
    }

    @Test
    void testGetAllStudents(){
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> expected = List.of(student1, student2);
        when(mockStudentRepo.findAll()).thenReturn(expected);

        assertEquals(2, studentService.getAllStudents().size());
        assertEquals(expected, studentService.getAllStudents());
    }

    @Test
    void testSaveStudent(){
        when(mockStudentRepo.save(mockStudent)).thenReturn(mockStudent);

        assertEquals(mockStudent, studentService.save(mockStudent));
    }
}
