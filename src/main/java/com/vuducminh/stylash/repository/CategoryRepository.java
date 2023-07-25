package com.vuducminh.stylash.repository;

import com.vuducminh.stylash.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Additional custom query methods can be added here
    Category findByName(String name);
    Category findById(Long id);
    List<Category> findByNameContaining(String name);
}

