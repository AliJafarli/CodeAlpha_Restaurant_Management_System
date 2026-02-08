package com.codealpha.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "menu_item_ingredients",
        uniqueConstraints = @UniqueConstraint(columnNames = {"menu_item_id", "inventory_item_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id")
    @JsonIgnore // prevent infinite recursion in JSON
    private MenuItem menuItem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_item_id")
    private InventoryItem inventoryItem;

    @Column(nullable = false)
    private BigDecimal amountPerItem; // amount to deduct per 1 menu item unit
}
