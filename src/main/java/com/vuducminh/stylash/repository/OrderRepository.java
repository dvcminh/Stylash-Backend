package com.vuducminh.stylash.repository;

import com.vuducminh.stylash.model.Order;
import com.vuducminh.stylash.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserEmailContaining(String email);
    List<Order> findByUser(User user);
    Optional<Order> findById(Long id);
    List<Order> findByUserId(Integer id);
    List<Order> findByShippingStatus(String shippingStatus);
    List<Order> findByPaymentStatus(String paymentStatus);
    List<Order> findAllByOrderByOrderDateDesc();
}

