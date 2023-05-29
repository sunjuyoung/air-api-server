package com.example.airbnbApi.review;

import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.listing.ListingRepository;
import com.example.airbnbApi.review.dto.RegisterReviewDTO;
import com.example.airbnbApi.review.dto.ResponseReviewDTO;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public void registerReview(RegisterReviewDTO registerReviewDTO) {
        Listing listing = listingRepository.findOnlyId(registerReviewDTO.getListing_id());
        Account account = userRepository.findOnlyId(registerReviewDTO.getReviewer_id());
        Review review = Review.createReview(listing, account, registerReviewDTO);
        review.setListing(listing);
        reviewRepository.save(review);

    }

    public  Page<ResponseReviewDTO>  getReviewsByListingId(Integer listing_id,int page) {

        PageRequest pageRequest = PageRequest.of(page-1, 4, Sort.by(Sort.Direction.DESC, "id"));
        //sorting 복잡해지면 쿼리로...
        Page<Review> reviews = reviewRepository.findAllByListingId(listing_id,pageRequest);
        if(reviews != null){
            Page<ResponseReviewDTO> result = reviews.map(review -> new ResponseReviewDTO(review));
//            List<ResponseReviewDTO> collect =
//                    reviews.stream().map(review -> new ResponseReviewDTO(review)).collect(Collectors.toList());
//            ResponsePageReview<ResponseReviewDTO> result = new ResponsePageReview<>(collect, reviews.getTotalPages());
            return result;
        }
        return null;
    }

//    public  record ResponsePageReview<T>(List<T>data,int totalPage){
//    }
}
