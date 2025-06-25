package com.akashkumar.unu.User.dto.Login;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userId;
    private String userName;
    private String userEmail;
    private String userMobile;
    private String userPassword;
    private Role role;

    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;
    private int totalSpend;
}
