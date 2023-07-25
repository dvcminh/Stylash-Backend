package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Like;
import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.user.User;

import java.util.List;
import java.util.Optional;

public interface LikeService {
    void unlikeProduct(User user, Product product);
    boolean isProductLikedByUser(User user, Product product);
    Like likeProduct(Like like);
    Optional<Like> findById(Integer id);
    List<Like> findByProductId(Integer id);
    List<Like> getAll();
    List<Like> findByNameContaining(String name);
    void detele(Like like);
    List<Like> findByUserId(Integer id);
}
