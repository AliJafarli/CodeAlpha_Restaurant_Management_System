package com.codealpha.restaurant.repo;

import com.codealpha.restaurant.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByAvailableTrue();

    @Query("""
    select distinct m
    from MenuItem m
    left join fetch m.ingredients i
    left join fetch i.inventoryItem
    where m.available = true
""")
    List<MenuItem> findPublicMenuWithIngredients();

    boolean existsByName(String name);
}
