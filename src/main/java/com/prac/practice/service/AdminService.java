package com.prac.practice.service;

import com.prac.practice.entity.User;
import com.prac.practice.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public void promoteToAdmin(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if ("ROLE_SUPER_ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Cannot modify SUPER_ADMIN");
        }

        user.setRole("ROLE_ADMIN");

        userRepository.save(user);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void demoteToUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if ("ROLE_SUPER_ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Cannot demote SUPER_ADMIN");
        }

        user.setRole("ROLE_USER");

        userRepository.save(user);
    }
}