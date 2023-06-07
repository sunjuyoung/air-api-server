package com.example.airbnbApi.user;

import com.example.airbnbApi.s3.S3Buckets;
import com.example.airbnbApi.s3.S3Service;
import com.example.airbnbApi.user.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private S3Service s3Service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private S3Buckets s3Buckets;

    private UserService service;

    @BeforeEach
    void setUp(){
//
//        service = new UserService(userRepository,
//                userMapper,
//                s3Service,
//                s3Buckets);

    }


    @Test
    public void test1(){

    }
}