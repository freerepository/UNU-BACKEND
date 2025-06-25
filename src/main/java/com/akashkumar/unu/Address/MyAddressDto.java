package com.akashkumar.unu.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyAddressDto {
    private String  myAddressId;

    private String userId;
    private String dealerId;
    private String courierId;

    private String username;
    private String userCity;
    private String userState;
    private int userPincode;
}
