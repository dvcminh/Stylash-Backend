package com.vuducminh.stylash.repository;

import com.vuducminh.stylash.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Additional custom query methods can be added here
    @Query(value = "SELECT p.* FROM products p JOIN likes l ON p.id = l.product_id GROUP BY p.id ORDER BY COUNT(l.id) DESC LIMIT 5", nativeQuery = true)
    List<Product> findTopLikedProducts();
    List<Product> findByCategoryNameContaining(String categoryName);
    List<Product> findByNameContaining(String name);
//    List<Product> findAllDesc();
//    List<Product> findAllAsc();
}

