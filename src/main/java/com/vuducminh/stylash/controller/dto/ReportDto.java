package com.vuducminh.stylash.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReportDto(Long id, String image, String title, String description, UserDto user) {

    public record UserDto(String username, String firstName, String lastName, String avatar) {
    }
}