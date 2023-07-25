package com.vuducminh.stylash.controller.dto;

import java.math.BigDecimal;

public record ProductDto(Integer id, String name, String image, CategoryDto categoryDto, String description, BigDecimal price) {

    public record CategoryDto(String name) {
    }
}
