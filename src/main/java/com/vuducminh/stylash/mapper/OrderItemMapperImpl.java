package com.vuducminh.stylash.mapper;

import com.vuducminh.stylash.controller.dto.CreateOrderItemRequest;
import com.vuducminh.stylash.controller.dto.CreateOrderRequest;
import com.vuducminh.stylash.controller.dto.OrderItemDto;
import com.vuducminh.stylash.model.OrderItem;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDto toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemDto.ProductDto productDto = new OrderItemDto.ProductDto(orderItem.getProduct().getName(), orderItem.getProduct().getImage_url());
        return new OrderItemDto(orderItem.getId(), productDto, orderItem.getQuantity(), orderItem.getPricePerUnit(), orderItem.getVoucherValue(), orderItem.getShippingValue(), orderItem.getSize(), orderItem.getColor());
    }

//    @Component
//    public class OrderItemMapper {
//        public OrderItemDto toDTO(OrderItem orderItem) {
//            OrderItemDto dto = new OrderItemDto();
//            dto.setId(orderItem.getId());
//            dto.setProductId(orderItem.getProduct().getId());
//            dto.setProductName(orderItem.getProduct().getName());
//            dto.setQuantity(orderItem.getQuantity());
//            dto.setPricePerUnit(orderItem.getPricePerUnit());
//            return dto;
//        }
//    }

}
