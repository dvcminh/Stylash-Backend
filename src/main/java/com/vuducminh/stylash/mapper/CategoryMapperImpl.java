package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.CategoryDto;
import com.vuducminh.stylash.controller.dto.UserDto;
import com.vuducminh.stylash.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapperImpl implements CategoryMapper{
    @Override
    public CategoryDto toCategoryDto(Category category) {
        if (category == null) {
            return null;
        }
//        List<UserDto.OrderDto> orders = user.getOrders().stream().map(this::toUserDtoOrderDto).toList();
        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }
}
