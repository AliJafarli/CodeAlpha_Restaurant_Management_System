package com.codealpha.restaurant.repo;

import com.codealpha.restaurant.domain.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findBySku(String sku);
    InventoryItem findFirstByOrderByIdAsc();

    boolean existsBySku(String sku);
}

