package com.codealpha.restaurant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "inventory_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private BigDecimal lowStockThreshold;

    @Column(nullable = false)
    private String unit; // e.g. pcs, g, ml
}
