package com.akashkumar.unu.ForgotPassword;

import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileResponse {
    private String status; //User Found
    private String email; //akp97583757@gmail.com
    private String message;  //Opt send successfully
}