package com.example.airbnbApi.listing;

import com.example.airbnbApi.category.Category;
import com.example.airbnbApi.common.BaseTime;
import com.example.airbnbApi.common.Map;

import com.example.airbnbApi.listing.dto.RegisterListingDTO;
import com.example.airbnbApi.review.Review;
import com.example.airbnbApi.user.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Listing extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "listing_id")
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Embedded
    private Map map;

//    @Column(nullable = false)
//    private String location;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private Integer guestCount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int roomCount;

    @Column(nullable = false)
    private int bathroomCount;

    private String imageSrc;

    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    @Builder.Default
    @OneToMany(mappedBy = "listing")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "listings", cascade = {CascadeType.ALL})
    private Set<Category> categories = new HashSet<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "listing_images",
            joinColumns = @JoinColumn(name = "image_id")
    )
    @Column(name = "images")
    private Set<String> images = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account host;



    public void setCategory(Category category){
        categories.add(category);
        category.getListings().add(this);
    }
    /**
     * 생성메서드
     */
    public static Listing createListing(Account account, RegisterListingDTO dto, Set<Category> category){
        Listing listing = Listing.builder()
                .description(dto.getDescription())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .map(new Map(dto.getLocation(), dto.getLatlng()))
                .bathroomCount(dto.getBathroomCount())
                .guestCount(dto.getGuestCount())
                .roomCount(dto.getRoomCount())
                .host(account)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .images(dto.getImages().stream().collect(Collectors.toSet()))
                .imageSrc(dto.getImages().stream().findFirst().get())
                .build();

        for(Category c : category){
            listing.setCategory(c);
        }

        return listing;
    }




}
