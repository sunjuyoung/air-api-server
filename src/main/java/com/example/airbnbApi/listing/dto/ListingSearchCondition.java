package com.example.airbnbApi.listing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingSearchCondition {

    private String locationValue;
    private String category;
    private String keyword;

    private LocalDate startDate;

    private LocalDate endDate;





}
