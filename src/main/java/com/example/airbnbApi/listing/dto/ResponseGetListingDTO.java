package com.example.airbnbApi.listing.dto;

import com.example.airbnbApi.category.Category;
import com.example.airbnbApi.common.Photo;
import com.example.airbnbApi.listing.Listing;
import com.example.airbnbApi.listing.vo.ReservationDateVO;
import com.example.airbnbApi.listing.vo.ReviewVO;
import com.example.airbnbApi.reservation.Reservation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ResponseGetListingDTO {

    private Integer id;


    private Integer userId;
    private String email;

    private String name;




    private String title;

    private String location;

    private int price;

    private String description;

    private int roomCount;

    private int bathroomCount;

    private int guestCount;

    private Set<String> category;
   //private String category;

    private String latlng;

    private Set<String> imageSrc = new HashSet<>();

    private LocalDate startDate;
    private LocalDate endDate;

    private List<ReviewVO> reviews;

    private List<ReservationDateVO> reservationDates;


    @QueryProjection
    public ResponseGetListingDTO(Set<String> imageSrc, LocalDate startDate,
                                 LocalDate endDate, List<ReviewVO> reviews,
                                 List<ReservationDateVO> reservationDates) {
        this.imageSrc = imageSrc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviews = reviews;
        this.reservationDates = reservationDates;
    }

    public ResponseGetListingDTO(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getHost().getName();
        this.userId = listing.getHost().getId();
        this.email = listing.getHost().getEmail();
        this.title = listing.getTitle();
        this.location = listing.getMap().getLocation();
        this.latlng = listing.getMap().getLatlng().replace("[", "").replace("]", "");;
        this.price = listing.getPrice();
        this.description = listing.getDescription();
        this.roomCount = listing.getRoomCount();
        this.bathroomCount = listing.getBathroomCount();
        this.guestCount = listing.getGuestCount();
        this.category = listing.getCategories().stream().map(Category::getName).collect(Collectors.toSet());
        this.startDate = listing.getStartDate();
        this.endDate = listing.getEndDate();

        for(String image:  listing.getImages()){
            this.imageSrc.add(image);
        }
    }

    public void setReservations(List<Reservation> reservations) {
       this.reservationDates =  reservations.stream()
                .map(reservation
                        -> new ReservationDateVO(reservation.getStartDate(),reservation.getEndDate()))
                .collect(Collectors.toList());
    }
}
