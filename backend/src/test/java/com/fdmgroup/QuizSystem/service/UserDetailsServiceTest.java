package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.dto.CustomUserDetails;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService mockUserService;


    @BeforeEach
    void setup(){
        CustomUserDetails customUserDetails = new CustomUserDetails();
        userDetailsService = new UserDetailsServiceImpl(mockUserService, customUserDetails);
    }

    @Test
    void testLoadByUsername(){
        User user = new User();
        user.setId(0);
        user.setUsername("test");
        user.setPassword("1");
        user.setEmail("test@gmail.com");
        user.setRole(Role.TRAINING);
        when(mockUserService.getUserByUsername("test")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("test");

        assertEquals(user.getId(), ((CustomUserDetails)result).getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getEmail(), ((CustomUserDetails)result).getEmail());
        assertEquals(List.of(new SimpleGrantedAuthority("TRAINING")), result.getAuthorities());
    }

    @Test
    void testMapUserToCustomUserDetails(){

        User user = new User();
        user.setId(0);
        user.setUsername("test");
        user.setPassword("1");
        user.setEmail("test@gmail.com");
        user.setRole(Role.TRAINING);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));

        CustomUserDetails result = userDetailsService.mapUserToCustomUserDetails(user, authorities);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(List.of(new SimpleGrantedAuthority("TRAINING")), result.getAuthorities());

    }
}
