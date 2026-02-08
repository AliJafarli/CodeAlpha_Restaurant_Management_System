package com.codealpha.restaurant.service;

import com.codealpha.restaurant.api.dto.CreateTableRequest;
import com.codealpha.restaurant.domain.RestaurantTable;
import com.codealpha.restaurant.repo.TableRepository;
import com.codealpha.restaurant.service.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public void create(CreateTableRequest request) {

        if (tableRepository.existsByTableNumber(request.getTableNumber())) {
            throw new ConflictException("Table already exists");
        }


        RestaurantTable table = RestaurantTable.builder()
                .tableNumber(request.getTableNumber())
                .capacity(request.getCapacity())
                .status(request.getStatus())
                .build();

        tableRepository.save(table);
    }

    public List<RestaurantTable> listAll() {
        return tableRepository.findAll();
    }
}

