package com.codealpha.restaurant.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuItemResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
    private List<MenuIngredientResponse> ingredients;
}

