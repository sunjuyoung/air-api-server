package com.example.airbnbApi.event;

import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.reservation.Reservation;
import lombok.Getter;

@Getter
public class ReservationEvent {


    private Listing listing;
    private String sendUser;


    public ReservationEvent(Listing listing,String sendUser) {
        this.listing = listing;
        this.sendUser = sendUser;
    }
}
