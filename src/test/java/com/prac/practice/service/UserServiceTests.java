package com.prac.practice.service;

import com.prac.practice.entity.User;
import com.prac.practice.exception.UserNotFoundException;
import com.prac.practice.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "ram",
            "alex",
            "demo",
            "ninj"
    })
    public void testGetByUser(String username) throws UserNotFoundException {
//        assertEquals(4,2+2);
        assertTrue(userRepository.findByUsername(username).isPresent(),"failed for name "+username);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,13,15",
            "3,4,8"
    })
    public void test(int a,int b ,int expected){
        assertEquals(expected,a+b);
    }
}
