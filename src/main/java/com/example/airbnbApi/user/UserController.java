package com.example.airbnbApi.user;

import com.example.airbnbApi.user.dto.FavoriteDTO;
import com.example.airbnbApi.user.dto.FavoriteListDTO;
import com.example.airbnbApi.user.mapper.UserMapper;
import com.example.airbnbApi.user.vo.FavoriteListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/favorite")
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteDTO favoriteDTO){
        userService.addFavorite(favoriteDTO);
        return ResponseEntity.ok().body(" success");
    }

    @DeleteMapping("/favorite/{listing_id}/{userId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Integer listing_id,
                                            @PathVariable Integer userId){

        userService.deleteFavorite(listing_id,userId);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/favorite/listingList/{account_id}")
    public ResponseEntity<?> getFavoriteListingList(@PathVariable Integer account_id){
        List<FavoriteListVO> result = userService.getFavoriteListingList(account_id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/favorite/{account_id}")
    public ResponseEntity<?> getFavoriteById(@PathVariable Integer account_id){
        Set<Integer> favorites = userService.getFavoriteByEmail(account_id);
       // Set<Integer> favoritesById = userMapper.getAccountWithFavoritesById(account_id);
        return ResponseEntity.ok().body(favorites);
    }


}
