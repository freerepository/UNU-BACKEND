package com.akashkumar.unu.Authentication.Registration;

import com.akashkumar.unu.Admin.dto.AdminDto;
import com.akashkumar.unu.Courier.dto.CourierDto;
import com.akashkumar.unu.Dealer.dto.DealerDto;
import com.akashkumar.unu.User.dto.UsersDto;
import com.akashkumar.unu.Utilities.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private Role role;
    private AdminDto admin;
    private DealerDto dealer;
    private CourierDto courier;
    private UsersDto user;
}
