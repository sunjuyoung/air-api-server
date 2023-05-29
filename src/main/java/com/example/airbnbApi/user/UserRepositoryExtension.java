package com.example.airbnbApi.user;

import com.example.airbnbApi.user.dto.FavoriteListDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public interface UserRepositoryExtension {


    List<FavoriteListDTO> getFavoriteListingList(Integer account_id);
}
