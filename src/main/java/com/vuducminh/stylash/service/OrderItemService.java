package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> findByOrderId(Long id);
}
