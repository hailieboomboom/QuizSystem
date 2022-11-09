package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.dto.CustomUserDetails;
import com.fdmgroup.QuizSystem.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Concrete class for the UserDetailService.
 * @author Jason Liu
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.getUserByUsername(username);
//        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
//        return mapUserToCustomUserDetails(user, authorities);
        return mapUserToCustomUserDetails(user);
    }

    /**
     * Map the user to UserDetails.
     * @param user User
     * @return     UserDetails
     */
    private CustomUserDetails mapUserToCustomUserDetails(User user) {

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setId(user.getId());
        customUserDetails.setUsername(user.getUsername());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setEmail(user.getEmail());

        return customUserDetails;
    }

}
