package com.fdmgroup.QuizSystem.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.StudentService;
import com.fdmgroup.QuizSystem.service.TrainerService;
import com.fdmgroup.QuizSystem.service.UserService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;
import com.fdmgroup.QuizSystem.util.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    @MockBean
    private SalesService salesService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelToDTO modelToDTO;

    @MockBean
    private TokenProvider tokenProvider;

    private Student student1;

    @BeforeEach
    void setup(){
        student1 = new Student();
        student1.setId(1);
        student1.setUsername("student1");
        student1.setPassword("1");
        student1.setEmail("student1@gmail.com");
        student1.setLastName("Liu");
        student1.setFirstName("Jason");

    }

    @Test
    void testGetStudentById() throws Exception {
        long id = 1;
        when(userService.getUserById(id)).thenReturn(student1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("student1")));

    }


}
