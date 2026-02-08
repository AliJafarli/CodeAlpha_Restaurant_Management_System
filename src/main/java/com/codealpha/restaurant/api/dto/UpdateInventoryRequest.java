package com.codealpha.restaurant.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateInventoryRequest {

    @NotNull
    private BigDecimal quantityDelta;
}
