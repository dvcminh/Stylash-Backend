package com.vuducminh.stylash.service;

import com.vuducminh.stylash.controller.dto.DailyRevenueDTO;
import com.vuducminh.stylash.model.Order;
import com.vuducminh.stylash.repository.OrderItemRepository;
import com.vuducminh.stylash.repository.OrderRepository;
import com.vuducminh.stylash.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final UserService userService;

    private EntityManager entityManager;

    @Override
    public List<Order> findAllOrdersSortedByDateDescending() {
        String jpql = "SELECT o FROM Order o ORDER BY o.orderDate DESC";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        return query.getResultList();
    }

    @Override
    public List<Order> viewAll() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    @Override
    public List<Order> findByShippingStatus(String shippingStatus) {
        return orderRepository.findByShippingStatus(shippingStatus);
    }

    @Override
    public List<Order> findByPaymentStatus(String shippingStatus) {
        return orderRepository.findByPaymentStatus(shippingStatus);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findByUserId(Integer id) {
        return orderRepository.findByUserId(id);
    }


    @Override
    public List<Order> getOrdersByUser(String email) {
        return orderRepository.findByUserEmailContaining(email);
    }

    @Override
    public List<DailyRevenueDTO> calculateDailyRevenue() {
        List<Order> orders = orderRepository.findAll();

        List<Order> sortedOrders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate))
                .distinct()
                .collect(Collectors.toList());


        List<Order> recentOrders = sortedOrders.stream()
                .limit(28)
                .toList();

        Map<LocalDate, BigDecimal> revenueByDate = recentOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getOrderDate().toLocalDate(),
                        LinkedHashMap::new,
                        Collectors.mapping(
                                Order::getTotalAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));


        List<DailyRevenueDTO> dailyRevenues = revenueByDate.entrySet().stream()
                .map(entry -> {
                    DailyRevenueDTO dailyRevenue = new DailyRevenueDTO();
                    dailyRevenue.setDate(entry.getKey());
                    dailyRevenue.setRevenue(entry.getValue());
                    return dailyRevenue;
                })
                .collect(Collectors.toList());

        return dailyRevenues;

    }



    @Override
    public BigDecimal calculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Order> getOrdersContainingText(String text) {
        User user = userService.validateAndGetUserByUsername(text);
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getOrdersByUserName(String userName) {
        return orderRepository.findByUserEmailContaining(userName);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
