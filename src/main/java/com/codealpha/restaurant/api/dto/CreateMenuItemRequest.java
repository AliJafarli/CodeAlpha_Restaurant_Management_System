package com.codealpha.restaurant.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateMenuItemRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private Boolean available;
}

