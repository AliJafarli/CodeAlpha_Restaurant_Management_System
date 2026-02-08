package com.codealpha.restaurant.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateInventoryRequest {

    @NotBlank
    private String sku;

    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal quantity;

    @NotNull
    @PositiveOrZero
    private BigDecimal lowStockThreshold;

    @NotBlank
    private String unit;
}
