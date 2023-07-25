package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.OrderDto;
import com.vuducminh.stylash.model.Order;

public interface OrderMapper {
    OrderDto toOrderDto(Order order);
}
