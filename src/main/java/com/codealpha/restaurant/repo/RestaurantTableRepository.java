package com.codealpha.restaurant.repo;

import com.codealpha.restaurant.domain.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    Optional<RestaurantTable> findByTableNumber(Integer tableNumber);
}
