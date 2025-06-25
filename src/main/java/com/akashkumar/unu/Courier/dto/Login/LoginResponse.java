package com.akashkumar.unu.Courier.dto.Login;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String courierId;
    private String courierName;
    private String courierEmail;
    private String courierMobile;
    private String courierPassword;
    private String dealerName;
    private Role role;

    private double totalEarning;
    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;
}
