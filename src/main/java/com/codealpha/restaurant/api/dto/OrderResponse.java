package com.codealpha.restaurant.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long tableId;
    private Integer tableNumber;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderLineResponse> items;
}

