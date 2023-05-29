package com.example.airbnbApi.review.dto;

import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.user.Account;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterReviewDTO {

    private Integer rating;

    private String comment;

    private String reviewer;
    private Integer reviewer_id;

    private Integer listing_id;

    public RegisterReviewDTO(Integer rating, String comment, String reviewer, Integer reviewer_id, Integer listing_id) {
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.reviewer_id = reviewer_id;
        this.listing_id = listing_id;
    }
}
