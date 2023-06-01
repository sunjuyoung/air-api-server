package com.example.airbnbApi.user;

import com.example.airbnbApi.s3.S3Buckets;
import com.example.airbnbApi.s3.S3Service;
import com.example.airbnbApi.user.dto.FavoriteDTO;
import com.example.airbnbApi.user.dto.FavoriteListDTO;
import com.example.airbnbApi.user.mapper.UserMapper;
import com.example.airbnbApi.user.vo.FavoriteListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    public void addFavorite(FavoriteDTO favoriteDTO){
        Account account = userRepository.findByEmail(favoriteDTO.getEmail()).orElseThrow();
        account.getFavorites().add(favoriteDTO.getListing_id());
    }


    public Set<Integer> getFavoriteByEmail(Integer account_id) {
        Account account = userRepository.findFavoritesById(account_id);
        Set<Integer> favorites = account.getFavorites();
        return favorites;
    }

    public void deleteFavorite(Integer listing_id,Integer userId) {
        Account account = userRepository.findFavoritesById(userId);
        account.getFavorites().remove(listing_id);
        //account.getFavorites().add(favoriteDTO.getListing_id());
    }

    public List<FavoriteListVO> getFavoriteListingList(Integer account_id) {

        //List<FavoriteListDTO> favoriteListingList = userRepository.getFavoriteListingList(account_id);
        List<FavoriteListVO> favoriteListByUserId = userMapper.getFavoriteListByUserId(account_id);
        return favoriteListByUserId;
    }

    private void updateUserProfileImageById(String profileImageId, Integer account_id){
        userRepository.updateProfileImageId(profileImageId,account_id);
    }

    public void uploadProfileImage(Integer account_id, MultipartFile file) {
        checkAccountIdExists(account_id);
        String profileImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Buckets.getAirbnb(),
                    "profile-images/%s/%s".formatted(account_id, profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //store profileImage to postgres
        updateUserProfileImageById(profileImageId,account_id);
    }


    public byte[] getUserProfileImage(Integer account_id) {
        Account account = userRepository.findOnlyId(account_id);

        //check if profileImage is empty or null
        var profileImageId = account.getProfileImageId();


        if(account.getProfileImageId().isBlank()){
            throw new RuntimeException("not found ProfileImage");
        }
        byte[] profileImage = s3Service.getObject(
                s3Buckets.getAirbnb(),
                "profile-images/%s/%s".formatted(account_id, profileImageId)
        );
        return profileImage;
    }

    private void checkAccountIdExists(Integer account_id) {
        if(!userRepository.existsAccountById(account_id)){
            throw new UsernameNotFoundException("not found".formatted(account_id));
        }

    }
}
