package com.codealpha.restaurant.repo;

import com.codealpha.restaurant.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}

