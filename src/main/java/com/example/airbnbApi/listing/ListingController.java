package com.example.airbnbApi.listing;

import com.example.airbnbApi.common.UploadDTO;
import com.example.airbnbApi.listing.dto.ListingSearchCondition;
import com.example.airbnbApi.listing.dto.RegisterListingDTO;
import com.example.airbnbApi.listing.dto.ResponseGetListingDTO;
import com.example.airbnbApi.listing.dto.ResponseListingListDTO;
import com.example.airbnbApi.listing.mapper.ListingMapper;
import com.example.airbnbApi.listing.vo.ListingVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/listing")
public class ListingController {

    private final ListingService listingService;

    @PostMapping
    public ResponseEntity<?> addListing(@RequestBody RegisterListingDTO dto){
        listingService.createListing(dto);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping
    public ResponseEntity<?> getAllListings(@RequestParam(value = "locationValue",required = false)String locationValue,
                                            @RequestParam(value = "category",required = false)String category,
                                            @RequestParam(value = "keyword",required = false)String keyword,
                                            @RequestParam(value = "page",required = false)Integer page,
            @RequestParam(value = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate startDate,
            @RequestParam(value = "endDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate endDate){
        ListingSearchCondition condition = new ListingSearchCondition(locationValue,category,keyword, startDate,endDate);
        Page<ResponseListingListDTO> result =
                listingService.getListingsWithSearchPage(condition, page);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{listing_id}")
    public ResponseEntity<?> getListingById(@PathVariable Integer listing_id){
        ResponseGetListingDTO getListing =  listingService.getListingById(listing_id);
        return ResponseEntity.ok().body(getListing);
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getListingsByUserId(@PathVariable Integer userId){
        List<ResponseListingListDTO> result = listingService.getListingsByUserId(userId);
        return ResponseEntity.ok().body(result);
    }


    @PostMapping(value = "/{account_id}/image",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> saveListingImage(@PathVariable("account_id") Integer account_id,
                                               @RequestParam("files") List<MultipartFile> files){
        Set<String> fileNames = listingService.saveListingImage(files, account_id);
        return ResponseEntity.ok().body(fileNames);

    }

    @GetMapping("/{listing_id}/{account_id}/image")
    public ResponseEntity<?> getListingImages(@PathVariable("listing_id")Integer listing_id,
                                              @PathVariable("account_id")Integer account_id){

        listingService.getListingImages(listing_id,account_id);
        return ResponseEntity.ok().body("success");

    }

}
