package com.example.airbnbApi.event;


import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.listing.ListingRepository;
import com.example.airbnbApi.notification.Notification;
import com.example.airbnbApi.notification.NotificationRepository;
import com.example.airbnbApi.notification.NotificationType;
import com.example.airbnbApi.reservation.Reservation;
import com.example.airbnbApi.reservation.ReservationRepository;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Log4j2
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class ReservationEventListener {

    private final NotificationRepository notificationRepository;
    private final ListingRepository listingRepository;
    private final ReservationRepository reservationRepository;



    @EventListener
    public void handleReservationEvent(ReservationEvent reservationEvent){
        Listing listing = reservationEvent.getListing();
        Listing listingWithHost = listingRepository.findListingWithHostById(listing.getId());
        Notification notification = Notification.builder()
                .title(listingWithHost.getTitle())
                .link("/api/v1/listing/"+ listingWithHost.getId())
                .checked(false)
                .createdLocalDateTime(LocalDateTime.now())
                .message("예약")
                .sendUser(reservationEvent.getSendUser())
                .account(listingWithHost.getHost())
                .notificationType(NotificationType.LISTING_RESERVATION)
                .build();
        notificationRepository.save(notification);

    }
}
