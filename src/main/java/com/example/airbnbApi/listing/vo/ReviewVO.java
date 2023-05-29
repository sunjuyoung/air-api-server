package com.example.airbnbApi.listing.vo;

import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.review.Review;
import com.example.airbnbApi.user.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewVO {

    private Integer id;

    private Integer rating;

    private String comment;

    private Integer listing_id;
    private String reviewer;
    private Integer reviewer_Id;


    public ReviewVO(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.listing_id = review.getListing().getId();
        this.reviewer = review.getReviewer().getName();
        this.reviewer_Id = review.getReviewer().getId();
    }

    public ReviewVO(Integer rating) {
        this.rating = rating;
    }
}
