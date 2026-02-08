package com.codealpha.restaurant.api;

import com.codealpha.restaurant.api.dto.UpdateInventoryRequest;
import com.codealpha.restaurant.api.dto.CreateInventoryRequest;
import com.codealpha.restaurant.domain.InventoryItem;
import com.codealpha.restaurant.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryItem> create(@Valid @RequestBody CreateInventoryRequest request) {
        return ResponseEntity.status(201).body(inventoryService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryItem>> list() {
        return ResponseEntity.ok(inventoryService.listInventory());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InventoryItem> update(@Valid @RequestBody UpdateInventoryRequest request) {
        return ResponseEntity.ok(inventoryService.updateInventory(request));
    }

    @GetMapping("/stock-alerts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryItem>> alerts() {
        return ResponseEntity.ok(inventoryService.lowStockAlerts());
    }
}
