package com.akashkumar.unu.Dealer.dto;

import com.akashkumar.unu.Bank.Bank;
import com.akashkumar.unu.Address.MyAddress;
import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealerDto {
    private String dealerId;
    private String dealerName;
    private String dealerEmail;
    private String dealerMobile;
    private String dealerPassword;
    private Role role;
    private Bank bankDetail;
    private boolean active;
    private boolean block;
    private boolean checkBank;
    private boolean checkAddress;
    private MyAddress myAddress;
    private double totalEarning;

    private String otp;
    private Date otpExpiry;

    private List<String> allProductsIds = new ArrayList<>();
    private List<String> categoriesIds = new ArrayList<>();
    private List<String> subCategoriesIds = new ArrayList<>();
    private List<String> usersIds = new ArrayList<>();

    private List<String> couriersIds = new ArrayList<>();
    /*****************ORDERS******************/
    private List<String> ordersIds = new ArrayList<>();
    private List<String> confirmOrdersIds = new ArrayList<>();
    private List<String> shippedOrdersIds = new ArrayList<>();
    private List<String> outOfDeliveryOrdersIds = new ArrayList<>();
    private List<String> delivered = new ArrayList<>();

}
