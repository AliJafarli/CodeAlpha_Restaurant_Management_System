package com.codealpha.restaurant.api;

import com.codealpha.restaurant.api.dto.CreateReservationRequest;
import com.codealpha.restaurant.domain.Reservation;
import com.codealpha.restaurant.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<Reservation> reserve(@Valid @RequestBody CreateReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }
}
