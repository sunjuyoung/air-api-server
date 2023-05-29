package com.example.airbnbApi.listing;

import com.example.airbnbApi.auth.AuthRequest;
import com.example.airbnbApi.auth.AuthService;
import com.example.airbnbApi.listing.dto.RegisterListingDTO;
import com.example.airbnbApi.user.Account;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ListingServiceTest {

    @Autowired
    private ListingService listingService;

    @Autowired
    private AuthService authService;




}