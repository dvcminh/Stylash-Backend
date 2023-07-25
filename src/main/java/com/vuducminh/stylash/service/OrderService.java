package com.vuducminh.stylash.service;

import com.vuducminh.stylash.controller.dto.DailyRevenueDTO;
import com.vuducminh.stylash.model.Order;
import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.user.User;
import org.aspectj.weaver.ast.Or;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    List<Order> findAllOrdersSortedByDateDescending();

    List<Order> viewAll();

    List<Order> findByShippingStatus(String shippingStatus);
    List<Order> findByPaymentStatus(String shippingStatus);

    Order createOrder(Order order);
    Order findById(Long id);
    List<Order> findByUserId(Integer id);
//    Order createOrderFromCartItems(List<Product> cartItems, User user);
    List<Order> getOrdersByUser(String email);

    List<DailyRevenueDTO> calculateDailyRevenue();

    BigDecimal calculateTotalRevenue();

    List<Order> getOrdersContainingText(String text);

    List<Order> getOrdersByUserName(String userName);

    void saveOrder(Order order);
}
