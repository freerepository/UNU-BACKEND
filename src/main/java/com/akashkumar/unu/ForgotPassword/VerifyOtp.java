package com.akashkumar.unu.ForgotPassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtp {
    private String email;
    private String otp;
}
