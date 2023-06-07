package com.example.airbnbApi.listing;

import com.example.airbnbApi.category.Category;
import com.example.airbnbApi.category.QCategory;
import com.example.airbnbApi.listing.dto.*;
import com.example.airbnbApi.reservation.QReservation;
import com.example.airbnbApi.review.QReview;
import com.example.airbnbApi.user.QAccount;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.example.airbnbApi.category.QCategory.*;
import static com.example.airbnbApi.listing.QListing.*;
import static com.example.airbnbApi.reservation.QReservation.*;
import static com.example.airbnbApi.review.QReview.*;
import static com.example.airbnbApi.user.QAccount.*;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.springframework.util.StringUtils.*;

public class ListingRepositoryExtensionImpl extends QuerydslRepositorySupport implements ListingRepositoryExtension {


    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     */
    public ListingRepositoryExtensionImpl() {
        super(Listing.class);
    }


    @Override
    public List<ResponseListingListDTO> getListingsByUserId(Integer userId) {
        JPQLQuery<ResponseListingListDTO> result = listingList();
        List<ResponseListingListDTO> fetch = result.where(account.id.eq(userId)).fetch();
        return fetch;

    }

    @Override
    public List<ResponseListingListDTO> listingListBySearch(ListingSearchCondition condition,Category category) {

        JPQLQuery<ResponseListingListDTO> listings = listingList();

        List<ResponseListingListDTO> fetch
                = listings.where(
                locationEq(condition.getLocationValue()),
                dateEq(condition.getStartDate(),condition.getEndDate()),
                categoryEq(category)
        ).fetch();



        return fetch;
    }

    @Override
    public List<Listing> listingListFetchJoin(ListingSearchCondition condition, Category category) {
        List<Listing> result = listingListFetchJoin().where(
                locationEq(condition.getLocationValue()),
                dateEq(condition.getStartDate(), condition.getEndDate()),
                categoryEq(category)
        ).fetch();
        return result;
    }

    @Override
    public Page<Listing> listingListPage(ListingSearchCondition condition, Category category, Pageable pageable) {
        QueryResults<Listing> listingQueryResults = listingListFetchJoin().where(
                locationEq(condition.getLocationValue()),
                dateEq(condition.getStartDate(), condition.getEndDate()),
                categoryEq(category)
        ).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<Listing> results = listingQueryResults.getResults();
        long total = listingQueryResults.getTotal();
        return new PageImpl<>(results,pageable,total);
    }

    @Override
    public Listing getListingByIdFetchJoin(Integer listing_id) {
        listingListFetchJoin().where(
                account.id.eq(listing_id)
        ).fetch();
        return null;
    }

    @Override
    public List<Tuple> getListingById(Integer listing_id) {
        List<Listing> fetch = selectFrom(listing).distinct()
                .innerJoin(account).on(listing.host.eq(account))
                .leftJoin(reservation).on(reservation.listing.eq(listing))
                .leftJoin(category).fetchJoin()
                .leftJoin(listing.reviews, review).fetchJoin()
                .where(account.id.eq(listing_id)).fetch();
        return null;
    }

    private BooleanExpression locationEq(String locationValue){
        return hasText(locationValue) ? listing.map.location.eq(locationValue) : null;
    }

    private BooleanExpression categoryEq(Category category){
        if(category != null){
            return  listing.categories.any().in(category);
        }
        return null;
    }
    private BooleanExpression dateEq(LocalDate startDate ,LocalDate endDate){
        if(startDate != null && endDate != null){
           return listing.startDate.lt(startDate).and(listing.endDate.gt(endDate));
        }
        return null;
    }

    private  JPQLQuery<ResponseListingListDTO> listingList() {
       return from(listing)
                .innerJoin(account).on(listing.host.eq(account))
                .leftJoin(reservation).on(reservation.listing.eq(listing))
                .leftJoin(category)
                .leftJoin(listing.reviews,review).fetchJoin()
                .select(new QResponseListingListDTO(
                        listing.id,
                        account.id,
                        listing.title,
                        listing.map.location,
                        listing.price,
                        listing.imageSrc,
                        listing.startDate,
                        listing.endDate
                ));
    }

    private JPQLQuery<Listing> listingListFetchJoin(){
       return from(listing).distinct()
                .innerJoin(account).on(listing.host.eq(account))
                .leftJoin(reservation).on(reservation.listing.eq(listing))
                .leftJoin(category).fetchJoin()
                .leftJoin(listing.reviews, review).fetchJoin()
                .orderBy(listing.id.desc())
                .select(listing);
    }




}
