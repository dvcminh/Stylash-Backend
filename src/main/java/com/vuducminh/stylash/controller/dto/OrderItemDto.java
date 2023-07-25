package com.vuducminh.stylash.controller.dto;

import com.vuducminh.stylash.model.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public record OrderItemDto(Long id, OrderItemDto.ProductDto product, Integer quantity, BigDecimal pricePerUnit, Integer voucherValue, Integer shippingValue, String size, String color) {

    public record ProductDto(String name, String avatar) {
    }
}
