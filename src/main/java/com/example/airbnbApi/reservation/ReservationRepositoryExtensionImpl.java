package com.example.airbnbApi.reservation;

import com.example.airbnbApi.listing.QListing;
import com.example.airbnbApi.listing.dto.QResponseListingListDTO;
import com.example.airbnbApi.reservation.dto.QReservationResponseDTO;
import com.example.airbnbApi.reservation.dto.ReservationResponseDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.airbnbApi.listing.QListing.*;
import static com.example.airbnbApi.reservation.QReservation.*;

public class ReservationRepositoryExtensionImpl extends QuerydslRepositorySupport implements ReservationRepositoryExtension {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     */
    public ReservationRepositoryExtensionImpl() {
        super(Reservation.class);
    }

    @Override
    public  List<ReservationResponseDTO> getReservationsByUser(Integer userId) {
        List<ReservationResponseDTO> result = from(reservation)
                .innerJoin(listing).on(listing.eq(reservation.listing))
                .where(reservation.guest.id.eq(userId))
                .select(new QReservationResponseDTO(
                        listing.id,
                        reservation.guest.id,
                        reservation.id,
                        reservation.startDate,
                        reservation.endDate,
                        reservation.totalPrice,
                        listing.map.location,
                        listing.categories.any().name,
                        listing.imageSrc
                )).fetch();

        return result;
    }
}
