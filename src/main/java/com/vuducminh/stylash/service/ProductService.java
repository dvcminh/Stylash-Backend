package com.vuducminh.stylash.service;

import com.vuducminh.stylash.model.Category;
import com.vuducminh.stylash.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product createProduct(String name, MultipartFile imageFile, String description, BigDecimal price, Category category) throws IOException;
    List<Product> getTopLikedProducts();
    List<Product> getProductDesc();
    List<Product> getProductAsc();

    List<Product> findbyNameContaining(String name);

    List<Product> viewAllByCategory();
    List<Product> viewAll();

    Product findById(Integer id);

    List<Product> findProductsByCategoryName(String categoryName);
    Product viewById(int id);
    Product save(Product product);

    void deleteProduct(Product product);
//    int getLikeCountForProduct(Product product);
}
