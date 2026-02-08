package com.codealpha.restaurant.api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySalesResponse {
    private LocalDate date;
    private BigDecimal totalSales;
    private Long ordersCount;
}
