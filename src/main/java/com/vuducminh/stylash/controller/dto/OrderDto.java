package com.vuducminh.stylash.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record OrderDto(Long id, UserDto user, LocalDateTime createdAt, BigDecimal totalAmount, String paymentStatus, String shippingStatus) {

    public record UserDto(String username, String firstName, String lastName, String avatar) {
    }
}