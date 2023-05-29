package com.example.airbnbApi.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FavoriteListDTO {

    private Integer listing_id;

    private String title;

    private Integer user_id;

    private String location;

    private int price;

    private LocalDate startDate;

    private LocalDate endDate;

    private String image_src;


    @QueryProjection
    public FavoriteListDTO(Integer listing_id, Integer user_id, String location, String title,
                           int price, String image_src, LocalDate startDate, LocalDate endDate) {
        this.listing_id = listing_id;
        this.user_id = user_id;
        this.location = location;
        this.title = title;
        this.price = price;
        this.image_src = image_src;
        this.startDate = startDate;
        this.endDate = endDate;


    }
}
