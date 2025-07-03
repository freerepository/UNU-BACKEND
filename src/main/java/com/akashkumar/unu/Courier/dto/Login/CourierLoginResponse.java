package com.akashkumar.unu.Courier.dto.Login;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierLoginResponse {
    private String courierId;
    private String name;
    private String email;
    private String mobile;
    private String password;

//    private String dealerName; // dealer name nahi rahega

    private Role role;

    private double totalEarning;
    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;
}
