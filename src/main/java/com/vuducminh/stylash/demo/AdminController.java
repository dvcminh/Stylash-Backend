package com.vuducminh.stylash.demo;

import com.vuducminh.stylash.controller.dto.*;
import com.vuducminh.stylash.mapper.OrderMapper;
import com.vuducminh.stylash.mapper.ProductMapper;
import com.vuducminh.stylash.mapper.ReportMapper;
import com.vuducminh.stylash.mapper.UserMapper;
import com.vuducminh.stylash.model.*;
import com.vuducminh.stylash.service.*;
import com.vuducminh.stylash.user.Role;
import com.vuducminh.stylash.user.User;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    private final UserService userService;
    private final UserMapper userMapper;

    private final ProductService productService;
    private final ProductMapper productMapper;

    private final CategoryService categoryService;

    private final VoucherService voucherService;

    private final ReportService reportService;
    private final ReportMapper reportMapper;

    @GetMapping("/getOrders")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<OrderDto> get() {
        return orderService.viewAll().stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<UserDto> getUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteUsers/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/getProducts")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.viewAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top-3-highest-spenders")
    public List<UserDto> getTop3HighestSpenders() {
        int limit = 3;
        return userService.getTopUsersWithHighestTotalAmount(limit).stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/total-revenue")
    public BigDecimal getTotalRevenue() {
        return orderService.calculateTotalRevenue();
    }

    @GetMapping("/daily-revenue")
    public List<DailyRevenueDTO> getDailyRevenue() {
        return orderService.calculateDailyRevenue();
    }

    @GetMapping("/getOrdersBySearch")
    public List<OrderDto> getOrders(@RequestParam(value = "text", required = false) String text,
                                    @RequestParam(value = "shippingStatus", required = false) String shippingStatus,
                                    @RequestParam(value = "paymentStatus", required = false) String paymentStatus) {
        List<Order> orders;

        if (shippingStatus != null) {
            orders = orderService.findByShippingStatus(shippingStatus);
        } else if (paymentStatus != null) {
            orders = orderService.findByPaymentStatus(paymentStatus);
        } else {
            if (text == null) {
                orders = orderService.viewAll();
            } else {
                orders = orderService.getOrdersByUserName(text);
            }
        }


        return orders.stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getProductsBySearch")
    public List<ProductDto> getProducts(@RequestParam(value = "name", required = false) String text,
                                        @RequestParam(value = "category", required = false) String category) {
        List<Product> products;

        if (category != null) {
            products = productService.findProductsByCategoryName(category);
            if (text != null) {
                products = products.stream()
                        .filter(product -> product.getName().toLowerCase().contains(text.toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else if (text != null) {
            products = productService.findbyNameContaining(text);
        } else {
            products = productService.viewAll();
        }

        return products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getUsersBySearch")
    public List<UserDto> getUsersByRoleAndName(@RequestParam(value = "role", required = false) String role,
                                               @RequestParam(value = "name", required = false) String name) {
        List<User> users;
        Role role1 = Role.valueOf("MANAGER");
        if (role == null || role.isEmpty()) {
            role = "MANAGER";
            role1 = Role.valueOf(role);
        }

        if (name == null || name.isEmpty()) {
            users = userService.getUsersByRole(role1);
        } else {
            users = userService.getUsersByRoleAndName(role1, name);
        }

        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{orderId}/shipping-status")
    public ResponseEntity<String> updateShippingStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("shippingStatus") String shippingStatus) {

        Order order = orderService.findById(orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        order.setShippingStatus(shippingStatus);
        orderService.saveOrder(order);

        return ResponseEntity.ok("Shipping status updated successfully");
    }

    @PutMapping("/{orderId}/payment-status")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable("orderId") Long orderId,
            @RequestParam("paymentStatus") String paymentStatus) {

        Order order = orderService.findById(orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        order.setPaymentStatus(paymentStatus);
        orderService.saveOrder(order);

        return ResponseEntity.ok("Payment status updated successfully");
    }

    @DeleteMapping("/deleteUser/{username}")
    public UserDto deleteUser(@PathVariable String username) {
        User user = userService.validateAndGetUserByUsername(username);
        userService.deleteUser(user);
        return userMapper.toUserDto(user);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id,
                                                @RequestParam(value = "image", required = false) String image,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "description", required = false) String description,
                                                @RequestParam(value = "price", required = false) BigDecimal price,
                                                @RequestParam(value = "category", required = false) String category) {

        Product updatedProduct = productService.findById(id);

        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        if (image != null) {
            updatedProduct.setImage_url(image);
        }
        if (name != null) {
            if (name != "null") {
                updatedProduct.setName(name);
            }
        }
        if (description != null) {
            if (description != "null") {
                updatedProduct.setDescription(description);
            }
        }
        if (price != null) {
            updatedProduct.setPrice(price);
        }
        if (category != null) {
            Category categoryProduct = categoryService.getCategoryByName(category);
            updatedProduct.setCategory(categoryProduct);
        }
        productService.save(updatedProduct);
        return ResponseEntity.ok("Thành công");
    }

    @PutMapping("/update-voucher/{id}")
    public ResponseEntity<String> updateVoucher(
            @PathVariable("id") Long id,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "expirationDate", required = false) LocalDate expirationDate) {

        Voucher existingVoucher = voucherService.getVoucherById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));

        existingVoucher.setCode(code);
        existingVoucher.setDiscount(price);
        existingVoucher.setExpirationDate(expirationDate);
        existingVoucher.setTitle(title);
        existingVoucher.setDescription(description);

        Voucher savedVoucher = voucherService.updateVoucher(existingVoucher);

        return ResponseEntity.ok("Payment status updated successfully");
    }

    @PostMapping("/createVoucher")
    public Voucher addProduct(@RequestParam(value = "code") String code,
                              @RequestParam(value = "description") String description,
                              @RequestParam(value = "discount") BigDecimal discount,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "expirationDate") LocalDate expirationDate) {
        Voucher voucher = new Voucher(code, discount, expirationDate, title, description);
        return voucherService.updateVoucher(voucher);
    }

    @DeleteMapping("/deleteVoucher/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long id) {
        Voucher existingVoucher = voucherService.getVoucherById(id)
                .orElseThrow(() -> new RuntimeException("Voucher not found with id: " + id));
        voucherService.deleteVoucher(existingVoucher);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.noContent().build();
        }
        productService.deleteProduct(product);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/getAllReport")
    public List<ReportDto> getAllReports(@RequestParam(value = "name",required = false)String name) {
        List<Report> reports;
        if (name != null) {
            reports = reportService.findAllByTitle(name);
        }
        else {
            reports = reportService.findAll();
        }
        return reports.stream()
                .map(reportMapper::toReportDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteReport/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        System.out.println("id");
        System.out.println(id);
        Report report = reportService.findById(id).orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
        reportService.deleteReport(report);
        return ResponseEntity.ok("Delete succesfully");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }
}
