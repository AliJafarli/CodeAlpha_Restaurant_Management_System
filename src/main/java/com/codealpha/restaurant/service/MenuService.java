package com.codealpha.restaurant.service;

import com.codealpha.restaurant.api.dto.AddMenuIngredientRequest;
import com.codealpha.restaurant.api.dto.CreateMenuItemRequest;
import com.codealpha.restaurant.api.dto.MenuIngredientResponse;
import com.codealpha.restaurant.api.dto.MenuItemResponse;
import com.codealpha.restaurant.domain.InventoryItem;
import com.codealpha.restaurant.domain.MenuItem;
import com.codealpha.restaurant.domain.MenuItemIngredient;
import com.codealpha.restaurant.repo.InventoryItemRepository;
import com.codealpha.restaurant.repo.MenuItemRepository;
import com.codealpha.restaurant.service.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public MenuItem create(CreateMenuItemRequest request) {
        MenuItem item = MenuItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .available(request.getAvailable())
                .build();
        if (menuItemRepository.existsByName(request.getName())) {
            throw new BadRequestException(
                    "Menu item with name '" + request.getName() + "' already exists"
            );
        }
        return menuItemRepository.save(item);
    }

    @Transactional
    public MenuItem addIngredient(AddMenuIngredientRequest request) {

        MenuItem menuItem = menuItemRepository.findById(request.getMenuItemId())
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        InventoryItem inventoryItem = inventoryItemRepository
                .findById(request.getInventoryItemId())
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found"));

        MenuItemIngredient ingredient = MenuItemIngredient.builder()
                .menuItem(menuItem)
                .inventoryItem(inventoryItem)
                .amountPerItem(request.getAmountPerItem())
                .build();

        menuItem.getIngredients().add(ingredient);

        return menuItemRepository.save(menuItem);
    }

    @Transactional(readOnly = true)
    public List<MenuItemResponse> listPublicMenu() {
        return menuItemRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private MenuItemResponse toResponse(MenuItem item) {
        return new MenuItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailable(),
                item.getIngredients().stream()
                        .map(i -> new MenuIngredientResponse(
                                i.getInventoryItem().getId(),
                                i.getInventoryItem().getSku(),
                                i.getAmountPerItem()
                        ))
                        .toList()
        );
    }

}
