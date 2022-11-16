package com.fdmgroup.QuizSystem.controller;
import com.fdmgroup.QuizSystem.dto.AuthResponse;
import com.fdmgroup.QuizSystem.dto.LoginRequest;
import com.fdmgroup.QuizSystem.dto.SignUpRequest;
import com.fdmgroup.QuizSystem.exception.RoleIsOutOfScopeException;
import com.fdmgroup.QuizSystem.exception.UserAlreadyExistsException;
import com.fdmgroup.QuizSystem.exception.UserUnauthorisedError;
import com.fdmgroup.QuizSystem.model.*;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.StudentService;
import com.fdmgroup.QuizSystem.service.TrainerService;
import com.fdmgroup.QuizSystem.service.UserService;
import com.fdmgroup.QuizSystem.util.TokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final TrainerService trainerService;
    private final SalesService salesService;
    private final TokenProvider tokenProvider;

    /**
     * Handle login request and return the jwt token.
     * @param loginRequest User input object containing username and password
     * @return             JWT token
     */


    @ApiOperation(value = "log in using username and password")
    @PostMapping("/login")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 401, message = "Either username or password is incorrect!"),
            @io.swagger.annotations.ApiResponse(code = 403, message = "You are not authorised. Please contact admin."),
    }
    )
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if(user.getRole().equals(Role.UNAUTHORISED_SALES) || user.getRole().equals(Role.UNAUTHORISED_TRAINER) || user.getRole().equals(Role.ABSENT)) {
            throw new UserUnauthorisedError("Your account is not authorised. Please contact an authorised staff");
        }
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    /**
     * Handle sign up request return the token to the frontend.
     * @param signUpRequest Object contains username, email and password.
     * @return              Token String.
     */
    @ApiOperation(value = "sign up using username, email and password")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 409, message = "Either username or email is used.")
    }
    )
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("user is currently signing up");
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        mapSignUpRequestToUserAndSave(signUpRequest);
        log.info("user with role {}, username {} and email {} and password {} is signed up successfully", signUpRequest.getRole(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    /**
     * Pass UsernamePasswordAuthenticationToken to the default AuthenticationProvider which will use the userDetails
     * service to get the user based on username and compare the encrypted password
     * @param username Username
     * @param password Password
     * @return         JWT String
     */

    @ApiOperation(value = "authenticate user using username and password, returns token")
    public String authenticateAndGetToken(String username, String password) {
        // Pass UsernamePasswordAuthenticationToken to the default AuthenticationProvider which will use the userDetails
        // service to get the user based on username and compare the encrypted password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    /**
     * Map the user input request to the User object. Role of a user should be "student", "trainer" or "sales"
     * @param signUpRequest Signup request object
     * @return              User object
     */
    @ApiOperation(value = "Map SignUpRequest to specific user based on the role value")
    private void mapSignUpRequestToUserAndSave(SignUpRequest signUpRequest) {

        switch (signUpRequest.getRole()) {
            case "student" -> {
                Student student = new Student();
                student.setEmail(signUpRequest.getEmail());
                student.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                student.setUsername(signUpRequest.getUsername());
                student.setFirstName(signUpRequest.getFirstName());
                student.setLastName(signUpRequest.getLastName());
                studentService.save(student);
            }
            case "trainer" -> {
                Trainer trainer = new Trainer();
                trainer.setEmail(signUpRequest.getEmail());
                trainer.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                trainer.setUsername(signUpRequest.getUsername());
                trainer.setFirstName(signUpRequest.getFirstName());
                trainer.setLastName(signUpRequest.getLastName());
                trainerService.save(trainer);
            }
            case "sales" -> {
                Sales sales = new Sales();
                sales.setEmail(signUpRequest.getEmail());
                sales.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                sales.setUsername(signUpRequest.getUsername());
                sales.setFirstName(signUpRequest.getFirstName());
                sales.setLastName(signUpRequest.getLastName());
                salesService.save(sales);
            }
            default -> throw new RoleIsOutOfScopeException();
        }
    }

}
