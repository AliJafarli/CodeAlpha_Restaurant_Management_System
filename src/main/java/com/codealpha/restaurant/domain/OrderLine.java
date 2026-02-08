package com.codealpha.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    @JsonBackReference
    private CustomerOrder order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;
}
