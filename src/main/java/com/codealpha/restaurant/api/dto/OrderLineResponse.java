package com.codealpha.restaurant.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLineResponse {
    private Long menuItemId;
    private String menuItemName;
    private Integer quantity;
}

