package com.prac.practice.service;

import com.prac.practice.entity.User;
import com.prac.practice.exception.UserNotFoundException;
import com.prac.practice.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder ;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(@NotBlank String username, @NotBlank String rawPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(user);
    }


    public User getByUser(@NotBlank String username){
        return userRepository.findByUsername(username).
                orElseThrow(()-> new UserNotFoundException("User Not Found"));
    }
}
