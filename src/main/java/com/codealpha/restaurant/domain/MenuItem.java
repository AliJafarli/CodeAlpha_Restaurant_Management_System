package com.codealpha.restaurant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean available;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenuItemIngredient> ingredients = new ArrayList<>();
}
