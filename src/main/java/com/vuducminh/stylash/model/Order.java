package com.vuducminh.stylash.model;
import com.vuducminh.stylash.user.User;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String shippingStatus;
    private String shippingAddress;

    public Order(User user, LocalDateTime orderDate, BigDecimal totalAmount, String paymentStatus, String shippingAddress, String shippingStatus) {
        this.user = user;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.shippingAddress = shippingAddress;
        this.shippingStatus = shippingStatus;
    }
}

