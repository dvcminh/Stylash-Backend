package com.vuducminh.stylash.controller.dto;

import com.vuducminh.stylash.user.Role;

import java.time.ZonedDateTime;
import java.util.List;

public record UserDto(Integer id, String email, String firstname, String lastname, String phoneNumber, String address, String avatar, Role role) {
    public record OrderDto(String id, String orderStatus) {
    }
}