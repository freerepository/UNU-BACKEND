package com.akashkumar.unu.User.entity;

import com.akashkumar.unu.Address.MyAddress;
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
@Document(collection = Urls.UserCollection)
public class Users {
    @Id
    private String userId;
    private String userName;
    private String userEmail;
    private String userMobile;
    private String userPassword;
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
