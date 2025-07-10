package com.akashkumar.unu.Admin.dto;

import com.akashkumar.unu.Courier.dto.CourierDto;
import com.akashkumar.unu.Dealer.dto.DealerDto;
import com.akashkumar.unu.User.dto.UsersDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDataResponse {

    private String message;

    private AdminDto adminData;
//    private List<UsersDto> users;
//    private List<DealerDto> dealers;
//    private List<CourierDto> couriers;

}
