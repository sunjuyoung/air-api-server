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

    @Column(unique = true)
    private String profileImageId;

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

    private int tokenGenerationCount;

    public void generateToken() {
        if(initializeTokenGenerationCount()){
            tokenGenerationCount = 0;
        }

        if (tokenGenerationCount < 3 && !checkGenerateTokenInterval()) {
            this.emailCheckToken = UUID.randomUUID().toString();
            this.emailCheckAt = LocalDateTime.now();
            tokenGenerationCount++;
        } else {
            throw new RuntimeException("토큰발행 횟수 초과 15분후 다시 시도해 주세요.");
        }
    }



    public void checkEmailToken(String token) {
        if(this.emailCheckToken.equals(token) && !checkConfirmEmailDate()){
            this.emailVerified = true;
        }
    }
    public boolean checkConfirmEmailDate() {
        return this.emailCheckAt.isBefore(LocalDateTime.now().minusMinutes(15));
    }

    private boolean checkGenerateTokenInterval() {
        return emailCheckAt != null && emailCheckAt.isAfter(LocalDateTime.now().minusMinutes(15));
    }

    private boolean initializeTokenGenerationCount(){
      return  tokenGenerationCount >=3 &&
                emailCheckAt != null &&
                emailCheckAt.isAfter(LocalDateTime.now().minusMinutes(15));
    }


    public Account(String name, String email, String password, boolean social) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.social = social;
        this.role = Role.MEMBER;
    }

    public void updateProfileImage(String profileImageId){
        this.profileImageId = profileImageId;
    }
}
