package com.codealpha.restaurant.repo;

import com.codealpha.restaurant.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
    boolean existsByTableNumber(Integer tableNumber);
}

