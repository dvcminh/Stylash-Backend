package com.vuducminh.stylash.controller;

import com.vuducminh.stylash.controller.dto.LikeDto;
import com.vuducminh.stylash.mapper.LikeMapper;
import com.vuducminh.stylash.model.Like;
import com.vuducminh.stylash.model.OrderItem;
import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.user.User;
import com.vuducminh.stylash.service.LikeService;
import com.vuducminh.stylash.service.ProductService;
import com.vuducminh.stylash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;
    private final LikeMapper likeMapper;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping()
    public List<LikeDto> findAll(@RequestParam(value = "name", required = false) String name){
        List<Like> likes;
        if (name != null) {
            likes = likeService.findByNameContaining(name);
        }
        else {
            likes = likeService.getAll();
        }
        return likes.stream()
                .map(likeMapper::toLikeDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/deleteLike/{id}")
    public ResponseEntity<String> deleteLike(@PathVariable Integer id) {
        Like like = likeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Like not found with id: " + id));

        likeService.detele(like);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{productId}")
    public void unlikeProduct(@PathVariable Integer productId, @AuthenticationPrincipal User user) {
        Product product = productService.viewById(productId);
        likeService.unlikeProduct(user, product);
    }

    @GetMapping("/{productId}/check")
    public boolean isProductLikedByUser(@PathVariable Integer productId, @AuthenticationPrincipal User user) {
        Product product = productService.viewById(productId);
        return likeService.isProductLikedByUser(user, product);
    }
}
