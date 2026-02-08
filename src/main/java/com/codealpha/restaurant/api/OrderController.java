package com.codealpha.restaurant.api;

import com.codealpha.restaurant.api.dto.CreateOrderRequest;
import com.codealpha.restaurant.api.dto.OrderResponse;
import com.codealpha.restaurant.domain.CustomerOrder;
import com.codealpha.restaurant.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<OrderResponse> place(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        CustomerOrder order = orderService.placeOrder(request);
        return ResponseEntity
                .status(201)
                .body(orderService.toResponse(order));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<List<OrderResponse>> list() {
        return ResponseEntity.ok(orderService.listAll());
    }

}

