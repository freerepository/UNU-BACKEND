package com.akashkumar.unu.Dealer.dto.Login;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealerLoginResponse {
    private String dealerId;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private Role role;
    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;
    private double totalEarning;
}
