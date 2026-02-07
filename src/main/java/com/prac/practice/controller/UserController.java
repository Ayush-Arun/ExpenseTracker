package com.prac.practice.controller;

import com.prac.practice.entity.User;
import com.prac.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.getByUser(username));
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser
            (@RequestParam String username,
             @RequestParam String password){
        return ResponseEntity.ok(userService.createUser(username, password));
    }
}
