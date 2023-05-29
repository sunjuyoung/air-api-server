package com.example.airbnbApi.listing;

import com.example.airbnbApi.category.Category;
import com.example.airbnbApi.listing.dto.ListingSearchCondition;
import com.example.airbnbApi.listing.dto.ResponseListingListDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ListingRepositoryExtension  {

     List<ResponseListingListDTO> getListingsByUserId(Integer userId);

     List<ResponseListingListDTO> listingListBySearch(ListingSearchCondition condition, Category category);

     List<Listing> listingListFetchJoin(ListingSearchCondition condition, Category category);

     Page<Listing> listingListPage(ListingSearchCondition condition, Category category, Pageable pageable);

     Listing getListingByIdFetchJoin(Integer listing_id);

     List<Tuple> getListingById(Integer listing_id);
}
