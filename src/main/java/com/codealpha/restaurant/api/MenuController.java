package com.codealpha.restaurant.api;

import com.codealpha.restaurant.api.dto.AddMenuIngredientRequest;
import com.codealpha.restaurant.api.dto.CreateMenuItemRequest;
import com.codealpha.restaurant.api.dto.MenuItemResponse;
import com.codealpha.restaurant.domain.MenuItem;
import com.codealpha.restaurant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> listMenu() {
        return ResponseEntity.ok(menuService.listPublicMenu());
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> createMenuItem(
            @RequestBody CreateMenuItemRequest request
    ) {
        return ResponseEntity.status(201).body(
                menuService.create(request)
        );
    }

    @PostMapping("/ingredients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addIngredient(
            @RequestBody AddMenuIngredientRequest request
    ) {
        menuService.addIngredient(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
