package com.codealpha.restaurant.service;

import com.codealpha.restaurant.api.dto.UpdateInventoryRequest;
import com.codealpha.restaurant.api.dto.CreateInventoryRequest;
import com.codealpha.restaurant.exception.DuplicateSkuException;
import org.springframework.transaction.annotation.Transactional;
import com.codealpha.restaurant.domain.InventoryItem;
import com.codealpha.restaurant.repo.InventoryItemRepository;
import com.codealpha.restaurant.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepository;

    @Transactional
    public InventoryItem create(CreateInventoryRequest request) {
        if (inventoryItemRepository.existsBySku(request.getSku())) {
            throw new DuplicateSkuException("Inventory item with sku '" + request.getSku() + "' already exists");
        }


        InventoryItem item = InventoryItem.builder()
                .sku(request.getSku())
                .name(request.getName())
                .quantity(request.getQuantity())
                .lowStockThreshold(request.getLowStockThreshold())
                .unit(request.getUnit())
                .build();
        return inventoryItemRepository.save(item);
    }

    public List<InventoryItem> listInventory() {
        return inventoryItemRepository.findAll();
    }

    @Transactional
    public InventoryItem updateInventory(UpdateInventoryRequest request) {
        // Request does not include ID (to keep controller unchanged as you requested).
        // We apply delta to the first inventory item (by id) deterministically.
        InventoryItem item = inventoryItemRepository.findFirstByOrderByIdAsc();
        if (item == null) throw new NotFoundException("No inventory items found. Add items via SQL/API first.");
        BigDecimal newQty = item.getQuantity().add(request.getQuantityDelta());
        item.setQuantity(newQty);
        return inventoryItemRepository.save(item);
    }

    public List<InventoryItem> lowStockAlerts() {
        return inventoryItemRepository.findAll().stream()
                .filter(i -> i.getQuantity().compareTo(i.getLowStockThreshold()) <= 0)
                .toList();
    }
}
