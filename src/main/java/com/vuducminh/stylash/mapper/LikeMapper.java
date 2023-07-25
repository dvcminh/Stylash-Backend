package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.LikeDto;
import com.vuducminh.stylash.controller.dto.OrderItemDto;
import com.vuducminh.stylash.model.Like;
import com.vuducminh.stylash.model.OrderItem;

public interface LikeMapper {
    LikeDto toLikeDto(Like like);
}
