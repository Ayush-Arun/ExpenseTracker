package com.prac.practice.service;

import com.prac.practice.dto.UserResponseDto;
import com.prac.practice.entity.User;
import com.prac.practice.exception.UserNotFoundException;
import com.prac.practice.exception.UsernameAlreadyExistsException;
import com.prac.practice.mapper.UserMapper;
import com.prac.practice.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.context.SecurityContextHolder;
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

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        return userRepository.save(user);
    }


    public UserResponseDto getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return getByUser(username);
    }

    //internal helper
    public UserResponseDto getByUser(@NotBlank String username){
        User user= userRepository.findByUsername(username).
                orElseThrow(()-> new UserNotFoundException("User Not Found"));
        return UserMapper.userToUserResponseDto(user);
    }
}
