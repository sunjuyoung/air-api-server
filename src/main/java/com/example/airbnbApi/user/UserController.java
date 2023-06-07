package com.example.airbnbApi.user;

import com.example.airbnbApi.user.dto.FavoriteDTO;
import com.example.airbnbApi.user.dto.FavoriteListDTO;
import com.example.airbnbApi.user.dto.ProfileResponseDTO;
import com.example.airbnbApi.user.mapper.UserMapper;
import com.example.airbnbApi.user.vo.FavoriteListVO;
import com.example.airbnbApi.user.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/profile/{account_id}")
    public ResponseEntity<?> getProfileById(@PathVariable("account_id") Integer account_id){
        ProfileResponseDTO profileResponseDTO =  userService.getUserById(account_id);
        return ResponseEntity.ok().body(profileResponseDTO);
    }

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

    @PostMapping(value = "/{account_id}/profile-image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadUserProfileImage(@PathVariable("account_id") Integer account_id,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        userService.uploadProfileImage(account_id,file);
    }

    @GetMapping(value = "/{account_id}/profile-image",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> getUserProfileImage(@PathVariable("account_id") Integer account_id){
        return ResponseEntity.ok().body(userService.getUserProfileImage(account_id));
    }


}
