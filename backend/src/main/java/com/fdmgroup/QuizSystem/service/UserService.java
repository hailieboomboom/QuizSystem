package com.fdmgroup.QuizSystem.service;
import com.fdmgroup.QuizSystem.dto.UserUpdateDTO;
import com.fdmgroup.QuizSystem.exception.UserAlreadyExistsException;
import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Role;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * User service to implement user CRUD relying on user repository.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * Get user by id.
     * @param id User id.
     * @return   User.
     */
    public User getUserById(long id){

        Optional<User> maybeUser = userRepository.findById(id);

        if(maybeUser.isEmpty()){
            throw new UserNotFoundException();
        }

        return maybeUser.get();
    }

    /**
     * Get user by the name.
     * @param username Username.
     * @return         User.
     */
    public User getUserByUsername(String username){
        Optional<User> maybe_user = userRepository.findUserByUsername(username);
        if(maybe_user.isEmpty()){
            throw new UserNotFoundException();
        }
        return maybe_user.get();
    }


    /**
     * Update user.
     * @param id           User id.
     * @param modifiedUser Updated information.
     * @return             User.
     */
    public User updateUser(long id, UserUpdateDTO modifiedUser) {
        Optional<User> maybeUser = userRepository.findById(id);

        if(maybeUser.isEmpty()){
            throw new UserNotFoundException();
        }


        if (userRepository.existsByEmail(modifiedUser.getEmail())) {
            throw new UserAlreadyExistsException(String.format("Email %s already been used", modifiedUser.getEmail()));
        }

        // Update user with new attributes
        User user = maybeUser.get();
        if(modifiedUser.getPassword() != null) {
            user.setPassword(modifiedUser.getPassword());
        }

        if(modifiedUser.getFirstName() != null) {
            user.setFirstName(modifiedUser.getFirstName());
        }
        if(modifiedUser.getLastName() != null) {
            user.setLastName(modifiedUser.getLastName());
        }
        if(modifiedUser.getEmail() != null) {
            user.setEmail(modifiedUser.getEmail());
        }

        if(modifiedUser.getRole() != null) {
            user.setRole(modifiedUser.getRole());
        }

        return userRepository.save(user);
    }

    /**
     * Get role by user id.
     * @param id User id.
     * @return   Role.
     */
    public Role getRoleByUserId(long id){

        Optional<User> maybeUser = userRepository.findById(id);

        if(maybeUser.isEmpty()){
            throw new UserNotFoundException();
        }

        return maybeUser.get().getRole();
    }

    /**
     * Check user's existence by username.
     * @param username Username.
     * @return         true or false.
     */
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    /**
     * Check user's existence by email.
     * @param email Email.
     * @return      true or false.
     */
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

}
