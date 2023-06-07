package com.example.airbnbApi.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3Buckets {

    private String airbnb;


    public String getAirbnb() {
        return airbnb;
    }

    public void setAirbnb(String airbnb) {
        this.airbnb = airbnb;
    }
}
