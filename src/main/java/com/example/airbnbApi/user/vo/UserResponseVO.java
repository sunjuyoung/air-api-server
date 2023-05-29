package com.example.airbnbApi.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseVO {

    private Integer account_id;
    private String email;
    private String name;
    private String favorite_ids;


}
