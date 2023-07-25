package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<Category> getAllCategories();

    Map<String, BigDecimal> getCategoryTotalAmounts();

    List<Category> getAllCategoriesByNameContaining(String name);
    Category getCategoryById(Integer categoryId);
    Category getCategoryByName(String name);
    Category createCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Integer categoryId);
}
