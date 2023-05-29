package com.example.airbnbApi.user.dto;

import lombok.Data;

@Data
public class FavoriteDTO {

    private Integer listing_id;
    private String email;
    private Integer favorite_listing_id;
}
