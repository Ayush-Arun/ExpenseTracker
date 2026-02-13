package com.prac.practice.mapper;

import com.prac.practice.dto.UserResponseDto;
import com.prac.practice.entity.User;

public class UserMapper {
    public static UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto(user.getId(), user.getUsername());
        return userResponseDto;
    }
}
