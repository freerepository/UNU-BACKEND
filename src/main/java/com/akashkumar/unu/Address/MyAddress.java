package com.akashkumar.unu.Address;

import com.akashkumar.unu.Utilities.Urls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Address")
public class MyAddress {
    @Id
    private String  myAddressId;

    private String userId;
    private String dealerId;
    private String courierId;

    private String username;
    private String userCity;
    private String userState;
    private int userPincode;
}
