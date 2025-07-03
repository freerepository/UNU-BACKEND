package com.akashkumar.unu.User.dto.Login;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String userId;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private Role role;

    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;
    private int totalSpend;
}
