package com.example.airbnbApi.reservation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationResponseDTO {

    private Integer listing_id;

    private Integer user_id;

    private Integer reservation_id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int totalPrice;

    private String location;

    private int price;

    private String categories;

    private String image_src;


    @QueryProjection
    public ReservationResponseDTO(Integer listing_id, Integer user_id, Integer reservation_id, LocalDate startDate, LocalDate endDate,
                                  int totalPrice, String location, String categories, String image_src) {
        this.listing_id = listing_id;
        this.user_id = user_id;
        this.reservation_id =reservation_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.location = location;
        this.categories = categories;
        this.image_src = image_src;
    }
}
