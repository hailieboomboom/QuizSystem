package com.fdmgroup.QuizSystem.controller;
import com.fdmgroup.QuizSystem.dto.UserOutputDTO;
import com.fdmgroup.QuizSystem.dto.UserUpdateDTO;
import com.fdmgroup.QuizSystem.exception.RoleIsOutOfScopeException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.StudentService;
import com.fdmgroup.QuizSystem.service.TrainerService;
import com.fdmgroup.QuizSystem.service.UserService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TrainerService trainerService;
    private final SalesService salesService;
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;
    private final ModelToDTO modelToDTO;

    @ApiOperation(value = "get user by id")
    @GetMapping("/{id}")
    public UserOutputDTO getUserById(@PathVariable long id){
        return modelToDTO.userToOutput(userService.getUserById(id));
    }

    @ApiOperation(value = "update user. If some fields are empty, then original values will be overwritten by empty values.")
    @PutMapping("/{id}")
    public UserOutputDTO updateUserById(@PathVariable long id, @RequestBody UserUpdateDTO modifiedUser){
        modifiedUser.setPassword(passwordEncoder.encode(modifiedUser.getPassword()));
        return modelToDTO.userToOutput(userService.updateUser(id, modifiedUser));
    }

    @ApiOperation(value = "get all unauthorised trainers.")
    @GetMapping("/trainers/unauthorised")
    public List<UserOutputDTO> getAllUnauthorisedTrainer(){
        return trainerService.getAllUnauthorisedTrainers().stream().map(modelToDTO::userToOutput).toList();
    }

    @ApiOperation(value = "get all unauthorised sales.")
    @GetMapping("/sales/unauthorised")
    public List<UserOutputDTO> getAllUnauthorisedSales(){
        return salesService.getAllUnauthorisedSales().stream().map(modelToDTO::userToOutput).toList();
    }

    @PutMapping("/trainers/authorise/{target_username}")
    public UserOutputDTO authoriseTrainer(@PathVariable String target_username){
        return modelToDTO.userToOutput(trainerService.authoriseTrainer(target_username));
    }

    @PutMapping("/sales/authorise/{target_username}")
    public UserOutputDTO authoriseSales(@PathVariable String target_username) {
        return modelToDTO.userToOutput(salesService.authoriseSales(target_username));
    }

    @PutMapping("/students/{target_username}/change-category/{category}")
    public UserOutputDTO alterStudentCategory(@PathVariable String target_username, @PathVariable String category){
        return modelToDTO.userToOutput(studentService.updateCategory(target_username, mapStringToRole(category)));
    }

    private Role mapStringToRole(String role) {

        switch (role.toUpperCase()) {
            case "ABSENT" -> {return Role.ABSENT; }
            case "TRAINING" -> { return Role.TRAINING; }
            case "POND" -> { return Role.POND; }
            case "BEACHED" -> { return Role.BEACHED; }
            default -> {throw new RoleIsOutOfScopeException();}
        }
    }





}
