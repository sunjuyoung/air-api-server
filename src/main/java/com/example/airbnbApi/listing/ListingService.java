package com.example.airbnbApi.listing;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
import com.example.airbnbApi.s3.S3Buckets;
import com.example.airbnbApi.s3.S3Service;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.UserRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
    private final ReservationRepository reservationRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    private final AmazonS3Client amazonS3Client;


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
            category =  categoryRepository.findOnlyCategoryByName(condition.getCategory()).get();
        }
        List<Listing> listings = listingRepository.listingListFetchJoin(condition,category);

        List<ResponseListingListDTO> result = listings.stream()
                .map(listing -> new ResponseListingListDTO(listing))
                .collect(Collectors.toList());
        return result;
    }

    public Page<ResponseListingListDTO> getListingsWithSearchPage(ListingSearchCondition condition, Integer page) {
        int pg;
        if(page == null){
            pg  = 1;
        }else{
            pg = page.intValue();
        }

        Category category =  categoryRepository.findOnlyCategoryByName(condition.getCategory()).orElse(null);
        Page<Listing> listings =
                listingRepository.listingListPage(condition, category, PageRequest.of(pg-1, 10, Sort.by("id").descending()));
        Page<ResponseListingListDTO> result = listings
                .map(listing -> new ResponseListingListDTO(listing));
        return result;
    }

    public Set<String> saveListingImage(List<MultipartFile> files,Integer account_id) {
        Set<String> fileNameList = new HashSet<>();
            files.forEach(file -> {
                try {
                    String profileImageId = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
                    s3Service.putObject(s3Buckets.getAirbnb(),
                            "listing-images/%s/listing/%s".formatted(account_id,profileImageId),
                            file.getBytes());
                    fileNameList.add(profileImageId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            return fileNameList;
    }

    public List<String> getListingImages(Integer listing_id, Integer account_id) {
        Listing listing = listingRepository.findListingWithImagesById(listing_id).orElseThrow();
        List<String> fileNameList = new ArrayList<>();
        listing.getImages().stream().map(image->{
            String url = amazonS3Client.getUrl(s3Buckets.getAirbnb(),
                    "listing-images/%s/listing/%s".formatted(account_id,image)).toString();
          return  fileNameList.add(url);
        });
        return fileNameList;








    }
}
