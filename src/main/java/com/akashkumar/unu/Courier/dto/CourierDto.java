package com.akashkumar.unu.Courier.dto;

import com.akashkumar.unu.Bank.Bank;
import com.akashkumar.unu.Address.MyAddress;
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
public class CourierDto {
    private String courierId;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String adharNumber;
//    private String dealerName;
    private Role role;
    private String panNumber;
    private String courierCity;


    private double totalEarning;
    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;

    private Bank bankDetail;
    private MyAddress courierAddress;

    private String otp;
    private Date otpExpiry;


    private List<String> dealers = new ArrayList<>();
    private List<String> ordersList = new ArrayList<>();
    private List<String> confirmOrdersIds = new ArrayList<>();
    private List<String> outOfDelivery = new ArrayList<>();
    private List<String> delivered = new ArrayList<>();
}
