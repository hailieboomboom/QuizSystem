package com.fdmgroup.QuizSystem.controller;
import com.fdmgroup.QuizSystem.dto.AuthResponse;
import com.fdmgroup.QuizSystem.dto.LoginRequest;
import com.fdmgroup.QuizSystem.dto.SignUpRequest;
import com.fdmgroup.QuizSystem.exception.UserAlreadyExistsException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.StudentService;
import com.fdmgroup.QuizSystem.service.TrainerService;
import com.fdmgroup.QuizSystem.service.UserService;
import com.fdmgroup.QuizSystem.util.TokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final StudentService studentService;

    private final TokenProvider tokenProvider;


    /**
     * Handle login request and return the jwt token.
     * @param loginRequest User input object containing username and password
     * @return             JWT token
     */

    @ApiOperation(value = "log in using username and password")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest){
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

    /**
     * Handle sign up request return the token to the frontend.
     * @param signUpRequest Object contains username, email and password.
     * @return              Token String.
     */
    @ApiOperation(value = "sign up using username, email and password")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUpStudent(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("user is currently signing up");
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        studentService.save(mapSignUpRequestToStudent(signUpRequest));
        //TODO: salesService and trainerService
        log.info("user with username {} and email {} and password {} is signed up successfully", signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getEmail() );
        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return new AuthResponse(token);
    }

    /**
     * Pass UsernamePasswordAuthenticationToken to the default AuthenticationProvider which will use the userDetails
     * service to get the user based on username and compare the encrypted password
     * @param username Username
     * @param password Password
     * @return         JWT String
     */
    @ApiOperation(value = "authenticate user using username and password, returns token")
    protected String authenticateAndGetToken(String username, String password) {
        // Pass UsernamePasswordAuthenticationToken to the default AuthenticationProvider which will use the userDetails
        // service to get the user based on username and compare the encrypted password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    /**
     * Map the user input request to the User object.
     * @param signUpRequest Signup request object
     * @return              User object
     */
    public Student mapSignUpRequestToStudent(SignUpRequest signUpRequest) {
        Student student = new Student();
        student.setEmail(signUpRequest.getEmail());
        student.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        student.setUsername(signUpRequest.getUsername());
        student.setFirstname(signUpRequest.getFirstname());
        student.setLastname(signUpRequest.getLastname());
//        user.setRole(WebSecurityConfig.USER);
        return student;
    }
}
