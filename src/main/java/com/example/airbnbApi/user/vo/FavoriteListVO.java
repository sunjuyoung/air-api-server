package com.example.airbnbApi.user.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteListVO {

    private Integer listing_id;

    private String title;

    private Integer account_id;

    private String location;

    private int price;

    private LocalDate startDate;

    private LocalDate endDate;

    private String image_src;



}
