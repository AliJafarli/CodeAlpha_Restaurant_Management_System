package com.codealpha.restaurant.service;

import com.codealpha.restaurant.api.dto.CreateReservationRequest;
import com.codealpha.restaurant.domain.Reservation;
import com.codealpha.restaurant.domain.RestaurantTable;
import com.codealpha.restaurant.repo.ReservationRepository;
import com.codealpha.restaurant.repo.RestaurantTableRepository;
import com.codealpha.restaurant.service.exception.ConflictException;
import com.codealpha.restaurant.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository tableRepository;

    @Transactional
    public Reservation createReservation(CreateReservationRequest request) {
        RestaurantTable table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new NotFoundException("Table not found: " + request.getTableId()));

        if (!reservationRepository.findOverlaps(table.getId(), request.getStartTime(), request.getEndTime()).isEmpty()) {
            throw new ConflictException("Table is not available for the selected time range.");
        }

        Reservation reservation = Reservation.builder()
                .table(table)
                .customerName(request.getCustomerName())
                .phone(request.getPhone())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        // mark reserved (simple)
        table.setStatus(RestaurantTable.TableStatus.RESERVED);
        tableRepository.save(table);

        return reservationRepository.save(reservation);
    }
}
