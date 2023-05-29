package com.example.airbnbApi.common;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Map {

    private String location;
    private String latlng;

    public Map(String location, String latlng) {
        this.location = location;
        this.latlng = latlng;
    }
}
