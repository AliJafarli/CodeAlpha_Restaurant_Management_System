package com.codealpha.restaurant.api.dto;

import com.codealpha.restaurant.domain.RestaurantTable;
import lombok.Data;

@Data
public class CreateTableRequest {
    private Integer tableNumber;
    private Integer capacity;
    private RestaurantTable.TableStatus status;
}

