package com.example.airbnbApi.listing;

import com.example.airbnbApi.category.Category;
import com.example.airbnbApi.category.CategoryRepository;
import com.example.airbnbApi.common.Photo;
import com.example.airbnbApi.common.PhotoRepository;
import com.example.airbnbApi.listing.dto.ListingSearchCondition;
import com.example.airbnbApi.listing.dto.RegisterListingDTO;
import com.example.airbnbApi.listing.dto.ResponseGetListingDTO;
import com.example.airbnbApi.listing.dto.ResponseListingListDTO;
import com.example.airbnbApi.listing.vo.ListingVO;
import com.example.airbnbApi.reservation.Reservation;
import com.example.airbnbApi.reservation.ReservationRepository;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.UserRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
  //  private final PhotoRepository photoRepository;

    private final ReservationRepository reservationRepository;


    public void createListing(RegisterListingDTO registerListingDTO){
        Account account = userRepository.findByEmail(registerListingDTO.getEmail()).get();

        Set<String> setCategory = new HashSet<>(Set.of(registerListingDTO.getCategory()));

        Set<Category> categories = categoryRepository.findByNameIn(setCategory);

        Listing listing = Listing.createListing(account,registerListingDTO,categories);
        Listing newListing = listingRepository.save(listing);
//        if(registerListingDTO.getImgPath() != null && !registerListingDTO.getImgPath().equals("")){
//            Photo photo = new Photo(registerListingDTO.getUuid(), registerListingDTO.getImgPath(), null,newListing);
//            photoRepository.save(photo);
//        }
    }


    public ResponseGetListingDTO getListingById(Integer listing_id) {
        Listing listing = listingRepository.findById(listing_id)
                .orElseThrow();
        List<Reservation> reservations = reservationRepository.findByListing(listing);
        ResponseGetListingDTO getListingDTO = new ResponseGetListingDTO(listing);
        getListingDTO.setReservations(reservations);
        return getListingDTO;

    }

    public List<ResponseListingListDTO> getListingsByUserId(Integer userId) {
        List<ResponseListingListDTO> result =
                listingRepository.getListingsByUserId(userId);
        return result;
    }


    public List<ResponseListingListDTO> getListingsWithSearch(ListingSearchCondition condition) {
        Category category = null;
        if(StringUtils.hasText(condition.getCategory())){
            category =  categoryRepository.findOnlyCategoryByName(condition.getCategory());
        }
        List<Listing> listings = listingRepository.listingListFetchJoin(condition,category);

        List<ResponseListingListDTO> result = listings.stream()
                .map(listing -> new ResponseListingListDTO(listing))
                .collect(Collectors.toList());
        return result;
    }

    public Page<ResponseListingListDTO> getListingsWithSearchPage(ListingSearchCondition condition, Integer page) {
        Category category = null;
        int pg;
        if(page == null){
            pg  = 1;
        }else{
            pg = page.intValue();
        }
        if(StringUtils.hasText(condition.getCategory())){
            category =  categoryRepository.findOnlyCategoryByName(condition.getCategory());
        }

        Page<Listing> listings =
                listingRepository.listingListPage(condition, category, PageRequest.of(pg-1, 10));
        Page<ResponseListingListDTO> result = listings
                .map(listing -> new ResponseListingListDTO(listing));
        return result;
    }
}
