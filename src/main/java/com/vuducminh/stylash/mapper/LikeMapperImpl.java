package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.LikeDto;
import com.vuducminh.stylash.controller.dto.OrderItemDto;
import com.vuducminh.stylash.model.Like;
import org.springframework.stereotype.Service;

@Service
public class LikeMapperImpl implements LikeMapper{
    @Override
    public LikeDto toLikeDto(Like like) {
        if (like == null) {
            return null;
        }

        LikeDto.ProductDto productDto = new LikeDto.ProductDto(like.getProduct().getName(), like.getProduct().getImage_url());
        LikeDto.UserDto userDto = new LikeDto.UserDto(like.getUser().getEmail(), like.getUser().getFirstname(), like.getUser().getLastname(), like.getUser().getAvatar());
        return new LikeDto(like.getId(), userDto, productDto);
    }
}
