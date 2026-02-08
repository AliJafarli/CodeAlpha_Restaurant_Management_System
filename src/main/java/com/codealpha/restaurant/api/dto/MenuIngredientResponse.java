package com.codealpha.restaurant.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MenuIngredientResponse {
    private Long inventoryItemId;
    private String sku;
    private BigDecimal amountPerItem;
}

