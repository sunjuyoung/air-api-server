package com.example.airbnbApi.reservation;

import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.review.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> ,ReservationRepositoryExtension{

    @EntityGraph(attributePaths = {"guest","listing"})
    Reservation findReservationWithListingAndGuestById(Integer id);



    List<Reservation> findByListing(Listing listing);
}
