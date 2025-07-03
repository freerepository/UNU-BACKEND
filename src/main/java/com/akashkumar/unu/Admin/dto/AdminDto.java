package com.akashkumar.unu.Admin.dto;

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
public class AdminDto {
    private String adminId;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private double totalEarning;
    private Role role;
    private Bank bankDetail;

    private boolean checkBank;

    private String otp;
    private Date otpExpiry;


    private List<String> users = new ArrayList<>();
    private List<String> dealers = new ArrayList<>();
    private List<String> couriers = new ArrayList<>();

    private List<String> blockedUser = new ArrayList<>();
    private List<String> blockedDealer = new ArrayList<>();
    private List<String> blockedCourier = new ArrayList<>();

    private List<String> activeUsers = new ArrayList<>();
    private List<String> activeDealers = new ArrayList<>();
    private List<String> activeCouriers = new ArrayList<>();
}
