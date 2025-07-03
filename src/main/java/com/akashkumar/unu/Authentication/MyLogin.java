package com.akashkumar.unu.Authentication;

import com.akashkumar.unu.Admin.dto.Login.LoginResponse;
import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Authentication.Login.LoginRequest;
import com.akashkumar.unu.Courier.dto.Login.CourierLoginResponse;
import com.akashkumar.unu.Dealer.dto.Login.DealerLoginResponse;
import com.akashkumar.unu.User.dto.Login.UserLoginResponse;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.repository.UsersRepository;
import com.akashkumar.unu.Utilities.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(Urls.Baseurl)
public class MyLogin implements AuthLogin{

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    DealerRepository dealerRepository;
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    UsersRepository usersRepository;


    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<Admin> admin = adminRepository.findByMobile(loginRequest.getMobile());
        Optional<Courier> courier = courierRepository.findByMobile(loginRequest.getMobile());
        Optional<Dealer> dealer = dealerRepository.findByMobile(loginRequest.getMobile());
        Optional<Users> users = usersRepository.findByMobile(loginRequest.getMobile());

        if (admin.isPresent()){
            Admin getAdminData = admin.get();
            if(getAdminData.getMobile().equals(loginRequest.getMobile()) && getAdminData.getPassword().equals(loginRequest.getPassword())){

                LoginResponse courierLoginResponse = new LoginResponse();
                courierLoginResponse.setAdminId(getAdminData.getAdminId());
                courierLoginResponse.setName(getAdminData.getName());
                courierLoginResponse.setEmail(getAdminData.getEmail());
                courierLoginResponse.setMobile(getAdminData.getMobile());
                courierLoginResponse.setRole(getAdminData.getRole());
                ApiResponse<LoginResponse> response = new ApiResponse<>("Login Successful", courierLoginResponse);
                return ResponseEntity.ok(response);

            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
            }
        }
        if (courier.isPresent()){
            Courier courierData = courier.get();
            if (courierData.isBlock()){
                throw new RuntimeException("You're Block. Please contect with admin");
            }
            if (courierData.getMobile().equals(loginRequest.getMobile()) && courierData.getPassword().equals(loginRequest.getPassword())){
                CourierLoginResponse courierLoginResponse = getCourierLoginResponse(courierData);
                ApiResponse<CourierLoginResponse> loginResponseApiResponse = new ApiResponse<>("Courier Login Successfully ", courierLoginResponse);
                return ResponseEntity.status(HttpStatus.OK).body(loginResponseApiResponse);
            }else {
                throw new RuntimeException("Something went wrong");
            }
        }
        if (dealer.isPresent()){
            Dealer dealerData = dealer.get();
            if (dealerData.isBlock()){
                throw new RuntimeException("You'r blocked. Please contect with admin");
            }
            if (dealerData.getMobile().equals(loginRequest.getMobile()) && dealerData.getPassword().equals(loginRequest.getPassword())){
                DealerLoginResponse dealerLoginResponse = getDealerLoginResponse(dealerData);
                ApiResponse<DealerLoginResponse> loginResponseApiResponse = new ApiResponse<>("Dealer Login Successfully ", dealerLoginResponse);
                return ResponseEntity.status(HttpStatus.OK).body(loginResponseApiResponse);
            }else {
                throw new RuntimeException("Something went wrong");
            }
        }
        if (users.isPresent()){
            Users userData = users.get();
            if (userData.isBlock()){ //true
                throw new RuntimeException("You're Block. Please contect with admin");
            }
            if (userData.getMobile().equals(loginRequest.getMobile()) && userData.getPassword().equals(loginRequest.getPassword())){
                UserLoginResponse userLoginResponse = getUserLoginResponse(userData);
                ApiResponse<UserLoginResponse> loginResponseApiResponse = new ApiResponse<>("User Login Successfully ", userLoginResponse);
                return ResponseEntity.status(HttpStatus.OK).body(loginResponseApiResponse);
            }else {
                throw new RuntimeException("Something went wrong");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("Something went wrong");
    }

    private static CourierLoginResponse getCourierLoginResponse(Courier courier) {
        CourierLoginResponse courierLoginResponse = new CourierLoginResponse();
        courierLoginResponse.setCourierId(courier.getCourierId());
        courierLoginResponse.setName(courier.getName());
        courierLoginResponse.setEmail(courier.getEmail());
        courierLoginResponse.setEmail(courier.getMobile());
        courierLoginResponse.setPassword(courier.getPassword());
        courierLoginResponse.setRole(courier.getRole());
        courierLoginResponse.setTotalEarning(courier.getTotalEarning());
        courierLoginResponse.setActive(courier.isActive());
        courierLoginResponse.setBlock(courier.isBlock());
        courierLoginResponse.setCheckBank(courier.isCheckBank());
        courierLoginResponse.setCheckAddress(courier.isCheckAddress());
//        courierLoginResponse.setDealerName(courier.getDealerName());
        return courierLoginResponse;
    }
    private static DealerLoginResponse getDealerLoginResponse(Dealer dealer) {
        DealerLoginResponse loginResponse = new DealerLoginResponse();
        loginResponse.setDealerId(dealer.getDealerId());
        loginResponse.setName(dealer.getName());
        loginResponse.setEmail(dealer.getEmail());
        loginResponse.setMobile(dealer.getMobile());
        loginResponse.setPassword(dealer.getPassword());
        loginResponse.setRole(dealer.getRole());
        loginResponse.setTotalEarning(dealer.getTotalEarning());
        loginResponse.setActive(dealer.isActive());
        loginResponse.setBlock(dealer.isBlock());
        loginResponse.setCheckBank(dealer.isCheckBank());
        loginResponse.setCheckAddress(dealer.isCheckAddress());
        return loginResponse;
    }
    private static UserLoginResponse getUserLoginResponse(Users users) {
        UserLoginResponse loginResponse = new UserLoginResponse();
        loginResponse.setUserId(users.getUserId());

        loginResponse.setName(users.getName());
        loginResponse.setEmail(users.getEmail());
        loginResponse.setPassword(users.getPassword());
        loginResponse.setMobile(users.getMobile());
        loginResponse.setRole(users.getRole());
        loginResponse.setTotalSpend(users.getTotalSpend());
        loginResponse.setActive(users.isActive());
        loginResponse.setBlock(users.isBlock());
        loginResponse.setCheckBank(users.isCheckBank());
        loginResponse.setCheckAddress(users.isCheckAddress());
        return loginResponse;
    }

}
interface AuthLogin{
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);
}
