package com.example.airbnbApi.reservation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Integer listing_id;

    private Integer user_id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int totalPrice;
}
