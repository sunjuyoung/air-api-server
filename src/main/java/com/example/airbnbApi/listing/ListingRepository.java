package com.example.airbnbApi.listing;

import com.example.airbnbApi.listing.dto.ResponseGetListingDTO;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing,Integer>,ListingRepositoryExtension{


    @BatchSize(size = 100)
    @Query("select l from Listing l " +
            " join fetch  Account " +
            " left join fetch Reservation  " +
            " left join fetch Category " +
            " where l.id = :listing_id")
    Optional<Listing> findById(@Param("listing_id")Integer listing_id);


    @BatchSize(size = 100)
    @Query("select l from Listing l " +
            " left join fetch Reservation  " +
            " where l.id = :listing_id")
    Optional<Listing> findListingWithReservationById(@Param("listing_id")Integer listing_id);



    @EntityGraph(attributePaths = {"host"})
    Listing findListingWithHostById(Integer listing_id);

    @EntityGraph(attributePaths = {"images"})
    Optional<Listing> findListingWithImagesById(Integer listing_id);



    @Query("select l  from Listing l where l.id = :listing_id")
    Listing findOnlyId(@Param("listing_id") int listing_id);






}
