package com.vuducminh.stylash.controller;

import com.vuducminh.stylash.model.Order;
import com.vuducminh.stylash.model.OrderItem;
import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.service.OrderItemService;
import com.vuducminh.stylash.service.OrderService;
import com.vuducminh.stylash.service.ProductService;
import com.vuducminh.stylash.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order_items")
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    @PostMapping("/createItem")
    public ResponseEntity<String> createOrderItem(@RequestParam("productId") int productId,
                                                  @RequestParam("orderId") long orderId,
                                                  @RequestParam("quantity") int quantity,
                                                  @RequestParam("pricePerUnit") BigDecimal pricePerUnit,
                                                  @RequestParam("size") String size,
                                                  @RequestParam("color") String color,
                                                  @RequestParam("voucher") int voucher,
                                                  @RequestParam("shipping") int shipping,
                                                  Authentication authentication) {

        Product product = productService.viewById(productId);

        Order order = orderService.findById(orderId);

        OrderItem orderItem = new OrderItem(order, product, quantity, pricePerUnit, size, color, voucher, shipping);

        orderItemService.createOrderItem(orderItem);

        return ResponseEntity.ok("Thành công!");
        // Gọi service để tạo đơn hàng mới và lưu các mục trong giỏ hàng
//        Order order = orderService.createOrderFromCartItems(cartItems, user);

        // Trả về thông báo thành công hoặc thất bại
//        if (order != null) {
//            return ResponseEntity.ok("Đơn hàng đã được tạo thành công.");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tạo đơn hàng.");
//        }
//        return null;
    }
}
