package com.vuducminh.stylash.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private Integer voucherValue;
    private Integer shippingValue;
    private String size;
    private String color;

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal pricePerUnit, String size, String color, Integer voucherValue, Integer shippingValue) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.size = size;
        this.color = color;
        this.voucherValue = voucherValue;
        this.shippingValue = shippingValue;
    }
}

