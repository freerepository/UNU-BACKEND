package com.akashkumar.unu.ForgotPassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassword {
    private String email;
    private String otp;
    private String password;
    private String confirmPassword;
}
