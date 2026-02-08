package com.codealpha.restaurant.repo;

import com.codealpha.restaurant.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(""" 
        select r from Reservation r
        where r.table.id = :tableId
          and ( (r.startTime < :end) and (r.endTime > :start) )
    """)
    List<Reservation> findOverlaps(Long tableId, LocalDateTime start, LocalDateTime end);
}
