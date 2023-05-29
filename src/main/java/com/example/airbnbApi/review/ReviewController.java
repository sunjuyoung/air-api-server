package com.example.airbnbApi.review;

import com.example.airbnbApi.review.dto.RegisterReviewDTO;
import com.example.airbnbApi.review.dto.ResponseReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {


    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> registerReview(@RequestBody RegisterReviewDTO registerReviewDTO){
        reviewService.registerReview(registerReviewDTO);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/{listing_id}")
    public ResponseEntity<?> getReviews(@PathVariable("listing_id")Integer listing_id,
                                        @RequestParam("page")int page){
        Page<ResponseReviewDTO> reviewsByListingId = reviewService.getReviewsByListingId(listing_id, page);
        return ResponseEntity.ok().body(reviewsByListingId);
    }

    @PutMapping("/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable("review_id")Integer review_id){
        return ResponseEntity.ok().body("");
    }
    @DeleteMapping("/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable("review_id")Integer review_id){
        return ResponseEntity.ok().body("");
    }

}
