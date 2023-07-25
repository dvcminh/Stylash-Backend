package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.user.User;
import com.vuducminh.stylash.controller.dto.UserDto;

public interface UserMapper {

    UserDto toUserDto(User user);
}