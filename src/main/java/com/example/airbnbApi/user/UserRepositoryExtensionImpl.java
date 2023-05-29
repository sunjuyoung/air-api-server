package com.example.airbnbApi.user;

import com.example.airbnbApi.listing.QListing;
import com.example.airbnbApi.user.dto.FavoriteListDTO;
import com.example.airbnbApi.user.dto.QFavoriteListDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Set;

import static com.example.airbnbApi.listing.QListing.*;
import static com.example.airbnbApi.user.QAccount.*;

public class UserRepositoryExtensionImpl extends QuerydslRepositorySupport implements UserRepositoryExtension {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     */
    public UserRepositoryExtensionImpl() {
        super(Account.class);
    }

    @Override
    public  List<FavoriteListDTO> getFavoriteListingList(Integer account_id) {

        List<FavoriteListDTO> fetch = from(account)
                .join(account.favorites)
                .innerJoin(listing).on(listing.host.eq(account))

                .select(new QFavoriteListDTO(
                        listing.id,
                        account.id,
                        listing.map.location,
                        listing.title,
                        listing.price,
                        listing.imageSrc,
                        listing.startDate,
                        listing.endDate
                ))   .where(account.id.eq(account_id)).fetch();

        return fetch;
    }
}
