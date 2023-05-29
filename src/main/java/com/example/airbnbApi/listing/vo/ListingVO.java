package com.example.airbnbApi.listing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingVO {

    private Integer listing_id;

    private String email;

    private String title;

    private String location;

    private int price;

    private String description;

    private int room_count;

    private int bathroom_count;

    private int guest_count;

    private String categories;

    private String image_src;

    private String favorite_ids;
}
