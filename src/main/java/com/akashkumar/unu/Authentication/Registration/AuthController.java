package com.akashkumar.unu.Authentication.Registration;

import com.akashkumar.unu.Admin.AdminServices.AdminServices;
import com.akashkumar.unu.Admin.dto.AdminDto;
import com.akashkumar.unu.Courier.dto.CourierDto;
import com.akashkumar.unu.Courier.services.CourierServices;
import com.akashkumar.unu.Dealer.dto.DealerDto;
import com.akashkumar.unu.Dealer.services.DealerService;
import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.User.dto.UsersDto;
import com.akashkumar.unu.User.services.UsersService;
import com.akashkumar.unu.Utilities.Enums.Role;
import com.akashkumar.unu.Utilities.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Urls.Baseurl)
public class AuthController implements Auth{
    @Autowired
    AdminServices adminServices;
    @Autowired
    DealerService dealerService;
    @Autowired
    UsersService usersService;
    @Autowired
    CourierServices courierServices;

    @Override
    @PostMapping("/register")
    public ResponseEntity<?> register(AuthRequest authRequest) {
         if (authRequest.getRole().equals(Role.ADMIN)){
             AdminDto adminDto = authRequest.getAdmin();
             AdminDto savedAdmin = adminServices.registerAdmin(adminDto);
             ApiResponse<AdminDto> response = new ApiResponse<AdminDto>("Admin Register Successfully", savedAdmin);
             return ResponseEntity.status(HttpStatus.CREATED).body(response);
         }else if (authRequest.getRole().equals(Role.USER)){
             UsersDto usersDto = authRequest.getUser();
             UsersDto usersDto1 = usersService.createUser(usersDto);
             ApiResponse<UsersDto> apiResponse = new ApiResponse<>("User Created Successfully ", usersDto1);
             return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

         }else  if (authRequest.getRole().equals(Role.COURIER)){
             CourierDto courierDto = authRequest.getCourier();
             CourierDto courierDto1 = courierServices.createCourier(courierDto);
             ApiResponse<CourierDto> apiResponse = new ApiResponse<>("Courier Register Successfully ", courierDto1);
             return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

         }else{
             DealerDto dto = authRequest.getDealer();
             DealerDto dealerDto = dealerService.registerDealerAccount(dto);
             ApiResponse<DealerDto> apiResponse = new ApiResponse<>("Dealer Registration Successfully ", dealerDto);
             return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
         }
    }
}
interface Auth{
    ResponseEntity<?> register(@RequestBody AuthRequest authRequest);
}
