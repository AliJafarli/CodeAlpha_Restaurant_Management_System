package com.codealpha.restaurant.api;

import com.codealpha.restaurant.api.dto.CreateTableRequest;
import com.codealpha.restaurant.domain.RestaurantTable;
import com.codealpha.restaurant.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody CreateTableRequest request) {
        tableService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTable>> list() {
        return ResponseEntity.ok(tableService.listAll());
    }
}

