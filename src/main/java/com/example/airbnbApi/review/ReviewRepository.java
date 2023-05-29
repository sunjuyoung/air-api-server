package com.example.airbnbApi.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Integer> {

    @EntityGraph(attributePaths = {"reviewer","listing"})
    @Query("select r from Review r where r.listing.id = :listing_id")    //countQuery
    Page<Review> findAllByListingId(@Param("listing_id")Integer listing_id, Pageable pageable);
}
