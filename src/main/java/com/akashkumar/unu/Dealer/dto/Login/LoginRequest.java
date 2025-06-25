package com.akashkumar.unu.Dealer.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String dealerMobile;
    private String dealerPassword;
}
