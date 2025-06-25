package com.akashkumar.unu.Dealer.dto.Login;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String dealerId;
    private String dealerName;
    private String dealerEmail;
    private String dealerMobile;
    private String dealerPassword;
    private Role role;
    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;
    private double totalEarning;
}
