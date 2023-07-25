package com.vuducminh.stylash.controller;

import com.vuducminh.stylash.controller.dto.CategoryDto;
import com.vuducminh.stylash.mapper.CategoryMapper;
import com.vuducminh.stylash.model.Category;
import com.vuducminh.stylash.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<Category> getAllCategories( @RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return categoryService.getAllCategoriesByNameContaining(name);
        }
        return categoryService.getAllCategories();
    }

    @GetMapping("/totals")
    public ResponseEntity<Map<String, BigDecimal>> getCategoryTotalAmounts() {
        Map<String, BigDecimal> categoryTotalAmounts = categoryService.getCategoryTotalAmounts();
        return new ResponseEntity<>(categoryTotalAmounts, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategoryById(@PathVariable Integer categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return categoryMapper.toCategoryDto(category);
    }

    @PostMapping
    public Category createCategory(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "description", required = false) String description) {
        Category category = new Category(name, description);
        return categoryService.createCategory(category);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getProductCount() {
        return ResponseEntity.ok(categoryService.getAllCategories().size());
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable Integer categoryId,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "description", required = false) String description) {

        Category updatedCategory = categoryService.getCategoryById(categoryId);
        updatedCategory.setName(name);
        updatedCategory.setDescription(description);
        return categoryService.updateCategory(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}

