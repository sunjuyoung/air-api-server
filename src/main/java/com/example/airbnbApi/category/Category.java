package com.example.airbnbApi.category;

import com.example.airbnbApi.listing.Listing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    private String icon;

    private String description;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "listing_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "listing_id"))
    private Set<Listing> listings = new HashSet<>();

//    @OneToMany(mappedBy = "category")
//    private Set<CategoryListing> categories = new HashSet<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id")
//    private Category parent;
//
//    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
//    private List<Category> children = new ArrayList<>();
}
