package com.example.airbnbApi.review.dto;

import com.example.airbnbApi.review.Review;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ResponseReviewDTO {


    private Integer id;
    private Integer rating;

    private String comment;

    private String reviewer;

    private Integer reviewer_id;

    private Integer listing_id;

    private LocalDate updated_at;

    public ResponseReviewDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.reviewer = review.getReviewer().getName();
        this.reviewer_id = review.getReviewer().getId();
        this.listing_id = review.getListing().getId();
        this.updated_at = review.getUpdatedAt();
    }
}
