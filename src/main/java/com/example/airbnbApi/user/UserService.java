package com.example.airbnbApi.user;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.example.airbnbApi.s3.S3Buckets;
import com.example.airbnbApi.s3.S3Service;
import com.example.airbnbApi.user.dto.FavoriteDTO;
import com.example.airbnbApi.user.dto.FavoriteListDTO;
import com.example.airbnbApi.user.dto.ProfileResponseDTO;
import com.example.airbnbApi.user.mapper.UserMapper;
import com.example.airbnbApi.user.vo.FavoriteListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final S3Buckets s3Buckets;

    private final S3Service s3Service;
    private final AmazonS3Client amazonS3Client;
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

    public void uploadProfileImage(Integer account_id, MultipartFile file) throws IOException {
        checkAccountIdExists(account_id);
        String profileImageId = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
//        File convertFile = convertMultipartFile(file);
//
//        amazonS3Client.putObject(new PutObjectRequest(s3Buckets.getAirbnb(),
//                "profile-images/%s/%s".formatted(account_id, profileImageId),
//                convertFile));
        s3Service.putObject(s3Buckets.getAirbnb(),
                "profile-images/%s/%s".formatted(account_id, profileImageId),
                file.getBytes());
        //removeNewFile(convertFile);
        //store profileImage to postgres
        updateUserProfileImageById(profileImageId,account_id);

    }

    private File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return uploadFile;
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
    public String getUserProfileImage(Integer account_id) {
        Account account = userRepository.findOnlyId(account_id);
        //check if profileImage is empty or null
        if(account.getProfileImageId() == null){
            return null;
        }
        var profileImageId = account.getProfileImageId();
        String url = amazonS3Client.getUrl(s3Buckets.getAirbnb(),
                "profile-images/%s/%s".formatted(account_id, profileImageId)).toString();
        return url;
    }

    private void checkAccountIdExists(Integer account_id) {
        if(!userRepository.existsAccountById(account_id)){
            throw new UsernameNotFoundException("not found".formatted(account_id));
        }

    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public ProfileResponseDTO getUserById(Integer account_id) {
        Account account = userRepository.findOnlyId(account_id);
        return new ProfileResponseDTO(account);
    }
}
