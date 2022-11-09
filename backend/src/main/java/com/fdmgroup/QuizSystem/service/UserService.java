package com.fdmgroup.QuizSystem.service;

import com.fdmgroup.QuizSystem.exception.UserNotFoundException;
import com.fdmgroup.QuizSystem.model.Student;
import com.fdmgroup.QuizSystem.model.User;
import com.fdmgroup.QuizSystem.repository.StudentRepository;
//import com.fdmgroup.QuizSystem.repository.UserRepository;
import com.fdmgroup.QuizSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Get user by id.
     * @param id User id.
     * @return   User.
     */
//    public User getUserById(long id){
//
//        Optional<User> maybeUser = studentRepository.findById(id);
//        if(maybeUser.isEmpty()){
//            throw new UserNotFoundException();
//        }
//        return maybeUser.get();
//    }

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
     * Delete user.
     * @param id User id.
     */
//    public void deleteUserById(long id){
//        if(userRepository.existsById(id)){
//            userRepository.deleteById(id);
//        }
//        else {
//            throw new UserNotFoundException();
//        }
//
//    }

    /**
     * Update user.
     * @param id           User id.
     * @param modifiedUser Updated information.
     * @return             User.
     */
//    public User updateUser(long id, User modifiedUser) {
//        Optional<User> maybeUser = userRepository.findById(id);
//        if(maybeUser.isEmpty()){
//            throw new UserNotFoundException();
//        }
//        // Update user with new attributes
//        User user = maybeUser.get();
////        user.setAvatar(input.getAvatar());
//        user.setUsername(modifiedUser.getUsername());
//        user.setPassword(modifiedUser.getPassword());
//        user.setEmail(modifiedUser.getEmail());
//        return userRepository.save(user);
//    }

    /**
     * Check user's existence by username.
     * @param username Username.
     * @return         true or false.
     */
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
//
//    /**
//     * Check user's existence by email.
//     * @param email Email.
//     * @return      true or false.
//     */
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
//
//    /**
//     * Persist user to the database.
//     * @param user User.
//     * @return     Persisted user.
//     */
//    public User save(User user) {
//        return userRepository.save(user);
//    }
}
