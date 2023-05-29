package com.example.airbnbApi.event;

import com.example.airbnbApi.user.Account;
import lombok.Getter;

@Getter
public class RegistrationCompleteEvent {

    private Account account;

    public RegistrationCompleteEvent(Account account) {
        this.account = account;
    }
}
