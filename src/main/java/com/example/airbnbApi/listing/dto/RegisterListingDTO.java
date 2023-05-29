package com.example.airbnbApi.listing.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterListingDTO {

    private String email;

    private String title;

    private String location;

    private int price;

    private String description;

    private int roomCount;

    private int bathroomCount;

    private int guestCount;

    private String[] category;

    private Set<String> images;

    private String imgPath;

    private String uuid;

    private String latlng;

    private String icon;

    private LocalDate startDate;
    private LocalDate endDate;


}
