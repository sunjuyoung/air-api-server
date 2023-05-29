package com.example.airbnbApi.reservation;

import com.example.airbnbApi.event.ReservationEvent;
import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.listing.ListingRepository;
import com.example.airbnbApi.reservation.dto.ReservationDTO;
import com.example.airbnbApi.reservation.dto.ReservationResponseDTO;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void createReservation(ReservationDTO reservationDTO) {
        Listing listing = listingRepository.findOnlyId(reservationDTO.getListing_id().intValue());
        Account account = userRepository.findOnlyId(reservationDTO.getUser_id().intValue());
        Reservation reservation = new Reservation(reservationDTO,account,listing);
        reservationRepository.save(reservation);
        eventPublisher.publishEvent(new ReservationEvent(listing, account.getName()));
    }

    public List<ReservationResponseDTO> getReservationByUser(Integer userId) {
        List<ReservationResponseDTO> result = reservationRepository.getReservationsByUser(userId);
        return result;
    }

    public void deleteReservation(Integer reservationId,Integer userId) {

    }
}
