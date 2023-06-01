package com.example.airbnbApi.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client(){
        S3Client client = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                // .endpointOverride(URI.create("https://s3.us-west-2.amazonaws.com"))
                //.forcePathStyle(true)
                .build();

        return client;
    }
}
