package com.vuducminh.stylash.repository;

import com.vuducminh.stylash.model.Like;
import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    void deleteByUserAndProduct(User user, Product product);

    boolean existsByUserAndProduct(User user, Product product);

    int countByProduct(Product product);

    List<Like> findByUserEmailContaining(String name);
    List<Like> findByProductId(Integer id);
    List<Like> findByUserId(Integer id);
}
