package com.example.airbnbApi.listing.dto;

import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.listing.vo.ReviewVO;
import com.example.airbnbApi.review.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseListingListDTO implements Serializable {


    private Integer listing_id;

    private Integer user_id;

    private String title;

    private String location;

    private int price;

    private String description;

    private int roomCount;

    private int bathroomCount;

    private int guestCount;

    private String category ;

    private String image_src ;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<ReviewVO> review;


    public ResponseListingListDTO(Listing listing) {
        this.listing_id = listing.getId();
        this.user_id = listing.getHost().getId();
        this.title = listing.getTitle();
        this.location = listing.getMap().getLocation();
        this.price = listing.getPrice();
        this.image_src = listing.getImageSrc();
        this.startDate = listing.getStartDate();
        this.endDate = listing.getEndDate();
        this.review = listing.getReviews().stream()
                .map(rev -> new ReviewVO(rev.getRating()))
                .collect(Collectors.toList());
    }

    @QueryProjection
    public ResponseListingListDTO(Integer listing_id, Integer user_id, String title,
                                  String location, int price, String image_src, LocalDate startDate,
                                  LocalDate endDate) {
        this.listing_id = listing_id;
        this.user_id = user_id;
        this.title = title;
        this.location = location;
        this.price = price;
        this.image_src = image_src;
        this.startDate = startDate;
        this.endDate = endDate;


       // this.category = category;
    }
}
