package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.CategoryDto;
import com.vuducminh.stylash.controller.dto.UserDto;
import com.vuducminh.stylash.model.Category;
import com.vuducminh.stylash.user.User;

public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
}
