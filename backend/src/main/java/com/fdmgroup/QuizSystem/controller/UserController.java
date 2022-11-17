package com.fdmgroup.QuizSystem.controller;
import com.fdmgroup.QuizSystem.dto.RoleDTO;
import com.fdmgroup.QuizSystem.dto.UserOutputDTO;
import com.fdmgroup.QuizSystem.dto.UserUpdateDTO;
import com.fdmgroup.QuizSystem.exception.RoleIsOutOfScopeException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.service.SalesService;
import com.fdmgroup.QuizSystem.service.StudentService;
import com.fdmgroup.QuizSystem.service.TrainerService;
import com.fdmgroup.QuizSystem.service.UserService;
import com.fdmgroup.QuizSystem.util.ModelToDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final TrainerService trainerService;
    private final SalesService salesService;
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;
    private final ModelToDTO modelToDTO;
    private final UserService userService;

    @ApiOperation(value = "get student by id")
    @GetMapping("/students/{id}")
    public ResponseEntity<UserOutputDTO> getStudentById(@PathVariable long id){
        return new ResponseEntity<>(modelToDTO.userToOutput(studentService.findStudentById(id)), HttpStatus.OK);
    }
    @ApiOperation(value = "get all students of all categories")
    @GetMapping("/students")
    public ResponseEntity<List<UserOutputDTO>> getAllStudents(){
        List<UserOutputDTO> resultList = studentService.getAllStudents().stream().map(modelToDTO::userToOutput).toList();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @ApiOperation(value = "update user. If some fields are empty, then original values will be overwritten by empty values.")
    @PutMapping("/students/{id}")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "Update successfully"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "User not found"),
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
            @io.swagger.annotations.ApiResponse(code = 409, message = "Either username or email already exists.")
    }
    )
    public ResponseEntity<UserOutputDTO> updateStudentById(@PathVariable long id, @RequestBody UserUpdateDTO modifiedUser){
        if(modifiedUser.getPassword() != null) {
            modifiedUser.setPassword(passwordEncoder.encode(modifiedUser.getPassword()));
        }
        return new ResponseEntity<>(modelToDTO.userToOutput(userService.updateUser(id, modifiedUser)), HttpStatus.OK);
    }

    @ApiOperation(value = "get trainer by id")
    @GetMapping("/trainers/{id}")
    public ResponseEntity<UserOutputDTO> getTrainerById(@PathVariable long id){
        return new ResponseEntity<>(modelToDTO.userToOutput(trainerService.getTrainerById(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "update user. If some fields are empty, then original values will be overwritten by empty values.")
    @PutMapping("/trainers/{id}")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "Update successfully"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "User not found"),
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
            @io.swagger.annotations.ApiResponse(code = 409, message = "Either username or email already exists.")

    }
    )
    public ResponseEntity<UserOutputDTO> updateTrainerById(@PathVariable long id, @RequestBody UserUpdateDTO modifiedUser){
        if(modifiedUser.getPassword() != null) {
            modifiedUser.setPassword(passwordEncoder.encode(modifiedUser.getPassword()));
        }
        return new ResponseEntity<>(modelToDTO.userToOutput(userService.updateUser(id, modifiedUser)), HttpStatus.OK);
    }

    @ApiOperation(value = "get sales by id")
    @GetMapping("/sales/{id}")
    public ResponseEntity<UserOutputDTO> getSalesById(@PathVariable long id){
        return new ResponseEntity<>(modelToDTO.userToOutput(salesService.getSalesById(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "update user. If some fields are empty, then original values will be overwritten by empty values.")
    @PutMapping("/sales/{id}")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "Update successfully"),
            @io.swagger.annotations.ApiResponse(code = 404, message = "User not found"),
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
            @io.swagger.annotations.ApiResponse(code = 409, message = "Either username or email already exists.")

    }
    )
    public ResponseEntity<UserOutputDTO> updateSalesById(@PathVariable long id, @RequestBody UserUpdateDTO modifiedSales){
        if(modifiedSales.getPassword() != null) {
            modifiedSales.setPassword(passwordEncoder.encode(modifiedSales.getPassword()));
        }
        return new ResponseEntity<>(modelToDTO.userToOutput(userService.updateUser(id, modifiedSales)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "get all unauthorised trainers.")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
    }
    )
    @GetMapping("/trainers/unauthorised")
    public ResponseEntity<List<UserOutputDTO>> getAllUnauthorisedTrainer(){
        return new ResponseEntity<>(trainerService.getAllUnauthorisedTrainers().stream().map(modelToDTO::userToOutput).toList(), HttpStatus.OK);
    }

    @ApiOperation(value = "get all unauthorised sales.")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
    }
    )
    @GetMapping("/sales/unauthorised")
    public ResponseEntity<List<UserOutputDTO>> getAllUnauthorisedSales(){
        return new ResponseEntity<>(salesService.getAllUnauthorisedSales().stream().map(modelToDTO::userToOutput).toList(), HttpStatus.OK);
    }

    @PutMapping("/trainers/authorise/{target_username}")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
    }
    )
    public ResponseEntity<UserOutputDTO> authoriseTrainer(@PathVariable String target_username){
        return new ResponseEntity<>(modelToDTO.userToOutput(trainerService.authoriseTrainer(target_username)), HttpStatus.OK);
    }

    @PutMapping("/sales/authorise/{target_username}")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
    }
    )
    public ResponseEntity<UserOutputDTO> authoriseSales(@PathVariable String target_username) {
        return new ResponseEntity<>(modelToDTO.userToOutput(salesService.authoriseSales(target_username)), HttpStatus.OK);
    }

    @PutMapping("categories/{category}/students/{target_username}/")
    @ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 403, message = "Authorisation failed"),
    }
    )
    public ResponseEntity<UserOutputDTO> alterStudentCategory(@PathVariable String target_username, @PathVariable String category){
        return new ResponseEntity<>(modelToDTO.userToOutput(studentService.updateCategory(target_username, mapStringToRole(category))), HttpStatus.OK);
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<RoleDTO> getRoleByUserId(@PathVariable long id){
        return new ResponseEntity<>(mapRoleToDTO(userService.getRoleByUserId(id)), HttpStatus.OK);
    }

    private RoleDTO mapRoleToDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole(role);
        return roleDTO;
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
