package com.example.airbnbApi.review;

import com.example.airbnbApi.common.BaseTime;
import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.review.dto.RegisterReviewDTO;
import com.example.airbnbApi.user.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Integer id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable =false)
    private Listing listing;

    public void setListing(Listing listing){
        this.listing = listing;
        listing.getReviews().add(this);
    }

    public static Review  createReview(Listing listing, Account account, RegisterReviewDTO dto) {
        Review review = Review.builder()
                .comment(dto.getComment())
                .listing(listing)
                .rating(dto.getRating())
                .reviewer(account)
                .build();
        return review;

    }
}
