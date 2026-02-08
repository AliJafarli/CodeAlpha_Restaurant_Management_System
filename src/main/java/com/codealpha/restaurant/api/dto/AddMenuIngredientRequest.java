package com.codealpha.restaurant.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddMenuIngredientRequest {

    @NotNull
    private Long menuItemId;

    @NotNull
    private Long inventoryItemId;

    @NotNull
    @Positive
    private BigDecimal amountPerItem;
}
