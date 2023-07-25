package com.vuducminh.stylash.service;

import com.cloudinary.Cloudinary;
import com.vuducminh.stylash.exception.OrderNotFoundException;
import com.vuducminh.stylash.model.Category;
import com.vuducminh.stylash.model.Product;
import com.vuducminh.stylash.repository.LikeRepository;
import com.vuducminh.stylash.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Cloudinary cloudinary;
    private final LikeRepository likeRepository;

    public String uploadProductImage(MultipartFile imageFile) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("folder", "product_images");

        Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), params);

        return uploadResult.get("secure_url").toString();
    }

    @Override
    public List<Product> getTopLikedProducts() {
        return productRepository.findTopLikedProducts();
    }

    @Override
    public List<Product> getProductDesc() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }

    @Override
    public List<Product> getProductAsc() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Product createProduct(String name, MultipartFile imageFile, String description, BigDecimal price, Category category) throws IOException {
        String imageUrl = uploadProductImage(imageFile);

        Product product = new Product();
        product.setName(name);
        product.setImage_url(imageUrl);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public List<Product> findbyNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

//    @Override
//    public int getLikeCountForProduct(Product product) {
//        return likeRepository.countByProduct(product);
//    }

    @Override
    public List<Product> viewAllByCategory() {
        return null;
    }

    @Override
    public List<Product> viewAll() {
        return productRepository.findAll();
    }

    @Override
    public Product viewById(int id) {
        return productRepository.findById(id).get();
    }


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product findById(Integer id){
        return productRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id %s not found", id)));
    }

    @Override
    public List<Product> findProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryNameContaining(categoryName);
    }
}
