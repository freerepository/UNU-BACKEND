package com.akashkumar.unu.Admin.entity;

import com.akashkumar.unu.Bank.Bank;
import com.akashkumar.unu.Utilities.Enums.Role;
import com.akashkumar.unu.Utilities.Urls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = Urls.AdminCollection)
public class Admin {
    @Id
    private String adminId;
    private String adminName;
    private String adminEmail;
    private String adminMobile;
    private String adminPassword;
    private double totalEarning;
    private Role role;
    private Bank bankDetail;

    private String otp;
    private Date otpExpiry;

    private boolean checkBank;

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
