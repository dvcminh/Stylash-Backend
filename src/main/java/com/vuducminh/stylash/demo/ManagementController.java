package com.vuducminh.stylash.demo;

import com.vuducminh.stylash.controller.dto.*;
import com.vuducminh.stylash.mapper.*;
import com.vuducminh.stylash.model.*;
import com.vuducminh.stylash.service.*;
import com.vuducminh.stylash.user.User;
import com.vuducminh.stylash.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@Tag(name = "Management")
public class ManagementController {

//    private final PasswordEncoder passwordEncoder;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private final UserService userService;
    private final UserMapper userMapper;

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    private final ProductService productService;
    private final ProductMapper productMapper;

    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    private final ReportService reportService;
    private final ReportMapper reportMapper;

    private final LikeService likeService;
    private final LikeMapper likeMapper;

    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )

    @GetMapping("/getUsers")
    public List<UserDto> getUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(currentUser.getUsername()));
    }

    @GetMapping("/getCategories")
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories().stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getWishlist/{id}")
    public List<LikeDto> getWishlist(@PathVariable Integer id) {
        return likeService.findByUserId(id).stream()
                .map(likeMapper::toLikeDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getOrdersById/{id}")
    public List<OrderDto> getOrdersById(@PathVariable Integer id) {
        List<Order> orders = orderService.findByUserId(id);

        return orders.stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/getOrderDetail/{id}")
    public List<OrderItemDto> getOrderDetailById(@PathVariable Long id) {
        List<OrderItem> orderItems = orderItemService.findByOrderId(id);

        return orderItems.stream()
                .map(orderItemMapper::toOrderItemDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{orderId}/shipping-status")
    public ResponseEntity<String> updateShippingStatus(@PathVariable Long orderId, @RequestBody String shippingStatus) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        order.setShippingStatus(shippingStatus);
        orderService.saveOrder(order);

        return ResponseEntity.ok("Shipping status updated successfully");
    }

    @PutMapping("/editUser")
    public UserDto editUser(@RequestParam(value = "firstname", required = false) String firstname,
                            @RequestParam(value = "lastname", required = false) String lastname,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "phone", required = false) String phone,
                            @RequestParam(value = "avatar", required = false) String avatar,
                            @AuthenticationPrincipal User currentUser) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());

        if (firstname != null) {
            user.setFirstname(firstname);
        }
        if (lastname != null) {
            user.setLastname(lastname);
        }
        if (firstname != null) {
        }
        if (firstname != null) {
        }
        if (firstname != null) {
        }

        user.setAddress(address);
        user.setPhone_number(phone);
        user.setAvatar(avatar);

        userService.updateUser(user);

        return userMapper.toUserDto(user);
    }



    @PostMapping("/createReport")
    public ResponseEntity<String> createReport(@RequestParam(value = "avatar") String avatar,
                                               @RequestParam(value = "title") String title,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "username") String username) {
        User user = userService.validateAndGetUserByUsername(username);

        Report report = new Report();
        report.setImage(avatar);
        report.setTitle(title);
        report.setDescription(description);
        report.setUser(user);

        reportService.save(report);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam(value = "oldpassword") String oldpassword,
                                                 @RequestParam(value = "newpassword") String newpassword,
                                                 @RequestParam(value = "email") String email) {
        User currentUser = userService.validateAndGetUserByUsername(email);
        if (!passwordEncoder.matches(oldpassword, currentUser.getPassword())) {
            return ResponseEntity.ok("Wrong old password");
        }
        currentUser.setPassword(passwordEncoder.encode(newpassword));
        userService.updateUser(currentUser);
        return ResponseEntity.ok("Update password successfully");
    }
}
