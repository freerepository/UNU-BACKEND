package com.akashkumar.unu.User.dto;

import com.akashkumar.unu.Address.MyAddress;
import com.akashkumar.unu.Bank.Bank;
import com.akashkumar.unu.Utilities.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class UsersDto {
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

    private MyAddress address;
    private Bank bankDetail;

    private String otp;
    private Date otpExpiry;

    /*iske andar product ids store hongi*/
    private List<String> carts  = new ArrayList<>(); //Products
    private List<String> orders = new ArrayList<>();
    private List<String> orderedHistory = new ArrayList<>();
}
