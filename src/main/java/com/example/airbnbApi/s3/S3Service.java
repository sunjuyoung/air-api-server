package com.example.airbnbApi.s3;

import com.amazonaws.services.s3.internal.InputSubstream;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.spring5.processor.SpringOptionFieldTagProcessor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

//
//
    public void putObject(String bucketName,String key, byte[] file){

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

      s3Client.putObject(objectRequest, RequestBody.fromBytes(file));

    }


//
//    public GetObjectResponse getObject(String bucketName,String key){
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .build();
//
//        ResponseInputStream<GetObjectResponse> res = s3Client.getObject(getObjectRequest);
//        try {
//            byte[] bytes = res.readAllBytes();
//            return bytes;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
