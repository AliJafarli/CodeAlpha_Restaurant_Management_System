package com.codealpha.restaurant.service;

import com.codealpha.restaurant.api.dto.CreateOrderRequest;
import com.codealpha.restaurant.api.dto.OrderLineResponse;
import com.codealpha.restaurant.api.dto.OrderResponse;
import com.codealpha.restaurant.domain.*;
import com.codealpha.restaurant.repo.CustomerOrderRepository;
import com.codealpha.restaurant.repo.InventoryItemRepository;
import com.codealpha.restaurant.repo.MenuItemRepository;
import com.codealpha.restaurant.repo.RestaurantTableRepository;
import com.codealpha.restaurant.service.exception.BadRequestException;
import com.codealpha.restaurant.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerOrderRepository orderRepository;
    private final RestaurantTableRepository tableRepository;
    private final MenuItemRepository menuItemRepository;
    private final InventoryItemRepository inventoryItemRepository;

    @Transactional
    public CustomerOrder placeOrder(CreateOrderRequest request) {
        RestaurantTable table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new NotFoundException("Table not found: " + request.getTableId()));

        if (table.getStatus() == RestaurantTable.TableStatus.OCCUPIED) {
            throw new BadRequestException("Table is occupied.");
        }

        CustomerOrder order = CustomerOrder.builder()
                .table(table)
                .status(CustomerOrder.OrderStatus.PLACED)
                .createdAt(LocalDateTime.now())
                .build();

        // Build lines
        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
                    .orElseThrow(() -> new NotFoundException("Menu item not found: " + itemReq.getMenuItemId()));

            if (Boolean.FALSE.equals(menuItem.getAvailable())) {
                throw new BadRequestException("Menu item is not available: " + menuItem.getName());
            }

            // Check and deduct inventory by recipe (ingredients)
            for (MenuItemIngredient ing : menuItem.getIngredients()) {
                InventoryItem inv = ing.getInventoryItem();
                BigDecimal needed = ing.getAmountPerItem().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
                if (inv.getQuantity().compareTo(needed) < 0) {
                    throw new BadRequestException("Not enough stock for SKU " + inv.getSku() + " (need " + needed + " " + inv.getUnit() + ")");
                }
            }
            // Deduct after validation
            for (MenuItemIngredient ing : menuItem.getIngredients()) {
                InventoryItem inv = ing.getInventoryItem();
                BigDecimal needed = ing.getAmountPerItem().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
                inv.setQuantity(inv.getQuantity().subtract(needed));
                inventoryItemRepository.save(inv);
            }

            OrderLine line = OrderLine.builder()
                    .order(order)
                    .menuItem(menuItem)
                    .quantity(itemReq.getQuantity())
                    .build();
            order.getItems().add(line);
        }

        table.setStatus(RestaurantTable.TableStatus.OCCUPIED);
        tableRepository.save(table);

        return orderRepository.save(order);
    }

    public OrderResponse toResponse(CustomerOrder order) {

        return OrderResponse.builder()
                .id(order.getId())
                .tableId(order.getTable().getId())
                .tableNumber(order.getTable().getTableNumber())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .items(
                        order.getItems().stream()
                                .map(line -> OrderLineResponse.builder()
                                        .menuItemId(line.getMenuItem().getId())
                                        .menuItemName(line.getMenuItem().getName())
                                        .quantity(line.getQuantity())
                                        .build())
                                .toList()
                )
                .build();
    }


    @Transactional(readOnly = true)
    public List<OrderResponse> listAll() {

        return orderRepository.findAll().stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .tableId(order.getTable().getId())
                        .status(order.getStatus().name())
                        .createdAt(order.getCreatedAt())
                        .items(
                                order.getItems().stream()
                                        .map(line -> OrderLineResponse.builder()
                                                .menuItemId(line.getMenuItem().getId())
                                                .menuItemName(line.getMenuItem().getName())
                                                .quantity(line.getQuantity())
                                                .build()
                                        )
                                        .toList()
                        )
                        .build()
                )
                .toList();
    }


}
