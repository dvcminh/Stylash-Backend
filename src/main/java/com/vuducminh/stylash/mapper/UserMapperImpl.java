package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.user.User;
import com.vuducminh.stylash.controller.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
//       List<UserDto.OrderDto> orders = user.getOrders().stream().map(this::toUserDtoOrderDto).toList();
        return new UserDto(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getPhone_number(), user.getAddress(), user.getAvatar(), user.getRole());
    }
}
