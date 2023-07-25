package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.CreateProductRequest;
import com.vuducminh.stylash.controller.dto.ProductDto;
import com.vuducminh.stylash.model.Product;

public interface ProductMapper {


    ProductDto toProductDto(Product product);
}
