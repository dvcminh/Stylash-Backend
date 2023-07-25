package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.CreateOrderItemRequest;
import com.vuducminh.stylash.controller.dto.OrderItemDto;
import com.vuducminh.stylash.model.OrderItem;

public interface OrderItemMapper {
//    OrderItem toOrder(CreateOrderItemRequest createOrderRequest);

    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
