package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Category;
import com.vuducminh.stylash.model.OrderItem;
import com.vuducminh.stylash.repository.CategoryRepository;
import com.vuducminh.stylash.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Override
    public Map<String, BigDecimal> getCategoryTotalAmounts() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        Map<String, BigDecimal> categoryTotalAmounts = new HashMap<>();

        for (OrderItem orderItem : orderItems) {
            Category category = orderItem.getProduct().getCategory();
            BigDecimal totalAmount = orderItem.getPricePerUnit().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

            categoryTotalAmounts.put(category.getName(), categoryTotalAmounts.getOrDefault(category.getName(), BigDecimal.ZERO).add(totalAmount));
        }

        return categoryTotalAmounts;
    }

    @Override
    public List<Category> getAllCategoriesByNameContaining(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}

