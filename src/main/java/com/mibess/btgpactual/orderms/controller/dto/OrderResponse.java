package com.mibess.btgpactual.orderms.controller.dto;

import java.math.BigDecimal;

import com.mibess.btgpactual.orderms.entity.OrderEntity;

import lombok.Builder;

@Builder
public record OrderResponse(Long orderId, Long customerId, BigDecimal total) {

    public static OrderResponse fromEntity(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .orderId(orderEntity.getId())
                .customerId(orderEntity.getCustumerId())
                .total(orderEntity.getTotal())
                .build();
    }
}
