package com.codealpha.restaurant.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReservationRequest {

    @NotNull
    private Long tableId;

    @NotBlank
    private String customerName;

    @NotBlank
    private String phone;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;
}
