package com.example.airbnbApi.user.dto;

import com.example.airbnbApi.user.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDTO {

    private Integer account_id;
    private String email;
    private String name;
    private boolean emailVerify;
    private String phoneNumber;
    private boolean social;
    private String profileImageId;

    public ProfileResponseDTO(Account account) {
        this.account_id = account.getId();
        this.email = account.getEmail();
        this.name = account.getName();
        this.emailVerify = account.isEmailVerified();
        this.phoneNumber = account.getPhoneNumber();
        this.social = account.isSocial();
        this.profileImageId = account.getProfileImageId();
    }
}
