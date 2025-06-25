package com.akashkumar.unu.Admin.dto.Login;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String adminId;
    private String adminName;
    private String adminEmail;
    private String adminMobile;
    private Role role;
}
