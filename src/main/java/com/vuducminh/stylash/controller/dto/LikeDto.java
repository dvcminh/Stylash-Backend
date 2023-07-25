package com.vuducminh.stylash.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LikeDto (Integer id, LikeDto.UserDto user, LikeDto.ProductDto product) {

    public record UserDto(String username, String firstName, String lastName, String avatar) {
    }

    public record ProductDto(String name, String avatar) {
    }
}
