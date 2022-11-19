package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.dto.UserUpdateDTO;
import com.fdmgroup.QuizSystem.exception.UserAlreadyExistsException;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository mockUserRepo;

    @Mock
    private User mockUser;

    @Mock
    private UserUpdateDTO mockUpdateDTO;

    @BeforeEach
    void setup(){
        userService = new UserService(mockUserRepo);
    }

    @Test
    void testGetUserById(){
        long id = 1;
        when(mockUserRepo.findById(id)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(id);

        assertEquals(mockUser, result);
    }

    @Test
    void testGetUserById_ThrowUserNotFoundException(){
        long id = 1;
        when(mockUserRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
    void testGetUserByUsername(){
        String username = "test";
        when(mockUserRepo.findUserByUsername(username)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserByUsername(username);

        assertEquals(mockUser, result);
    }

    @Test
    void testGetUserByUsername_ThrowUserNotFoundException(){
        String username = "test";
        when(mockUserRepo.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void testUpdateUser(){
        long id = 1;
        User user = new User();
        user.setId(id);
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setEmail("test@gmail.com");
        user.setPassword("1");
        user.setRole(Role.TRAINING);
        user.setUsername("Jason");

        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setFirstName("firstname");
        userUpdateDTO.setLastName("lastname");
        userUpdateDTO.setEmail("test@gmail.com");
        userUpdateDTO.setPassword("1");
        userUpdateDTO.setRole(Role.POND);

        when(mockUserRepo.findById(id)).thenReturn(Optional.of(user));
        when(mockUserRepo.save(user)).thenReturn(user);

        User result = userService.updateUser(id, userUpdateDTO);
        assertEquals(userUpdateDTO.getRole(), result.getRole());
        assertEquals(userUpdateDTO.getPassword(), result.getPassword());
        assertEquals(userUpdateDTO.getEmail(), result.getEmail());
        assertEquals(userUpdateDTO.getLastName(), result.getLastName());
        assertEquals(userUpdateDTO.getFirstName(), result.getFirstName());
    }

    @Test
    void testUpdateUser_ThrowUserNotFoundException(){
        long id = 1;
        when(mockUserRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(id, mockUpdateDTO));
    }

    @Test
    void testUpdateUser_ThrowUserAlreadyExistsException(){
        long id = 1;
        String email = "test@gmail.com";
        when(mockUserRepo.findById(id)).thenReturn(Optional.of(mockUser));
        when(mockUpdateDTO.getEmail()).thenReturn(email);
        when(mockUserRepo.existsByEmail(email)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(id, mockUpdateDTO));
    }

    @Test
    void testGetRoleByUserId(){
        long id = 1;
        User user = new User();
        user.setRole(Role.TRAINING);
        when(mockUserRepo.findById(id)).thenReturn(Optional.of(user));

        Role result = userService.getRoleByUserId(id);

        assertEquals(Role.TRAINING, result);
    }

    @Test
    void testGetRoleByUserId_ThrowUserNotFoundException(){
        long id = 1;
        when(mockUserRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getRoleByUserId(id));
    }

    @Test
    void testExistsByUsername_ReturnTrue(){
        String username = "test";
        when(mockUserRepo.existsByUsername(username)).thenReturn(true);

        boolean result = userService.existsByUsername(username);

        assertTrue(result);
    }

    @Test
    void testExistsByEmail_ReturnTrue(){
        String email = "test@gmail.com";
        when(mockUserRepo.existsByEmail(email)).thenReturn(true);

        boolean result = userService.existsByEmail(email);

        assertTrue(result);
    }

}
