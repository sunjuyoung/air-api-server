package com.example.airbnbApi.user;

import com.example.airbnbApi.common.BaseTime;
import com.example.airbnbApi.listing.Listing;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account  extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean emailVerified;

    private LocalDateTime emailCheckAt;

    private String emailCheckToken;

    private String phoneNumber;

    private boolean social;

    private String image;

    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "favorite_id")
    )
    @Column(name = "favorite_listing_id")
    private Set<Integer> favorites = new HashSet<>();


    public void generateToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckAt = LocalDateTime.now();
    }

    public void checkEmailToken(String token) {
        if(this.emailCheckToken.equals(token) && !checkConfirmEmailDate()){
            this.emailVerified = true;
        }
    }
    public boolean checkConfirmEmailDate() {
        return this.emailCheckAt.isBefore(LocalDateTime.now().minusMinutes(15));
    }




}
