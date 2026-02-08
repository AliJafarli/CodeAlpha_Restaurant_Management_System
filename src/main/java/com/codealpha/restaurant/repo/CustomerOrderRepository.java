package com.codealpha.restaurant.repo;

import com.codealpha.restaurant.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
