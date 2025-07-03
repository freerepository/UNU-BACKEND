package com.akashkumar.unu.Admin.controller;

import com.akashkumar.unu.Admin.AdminServices.AdminServices;
import com.akashkumar.unu.Admin.dto.AdminDto;
import com.akashkumar.unu.Admin.dto.BlockDealerRequest;
import com.akashkumar.unu.Admin.dto.Login.LoginRequest;
import com.akashkumar.unu.Admin.dto.Login.LoginResponse;
import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Courier.dto.CourierDto;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.mapper.CourierMapper;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.Dealer.dto.DealerDto;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.mapper.DealerMapper;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.ForgotPassword.ForgotPasswordService;
import com.akashkumar.unu.User.dto.UsersDto;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.mapper.UsersMapper;
import com.akashkumar.unu.User.repository.UsersRepository;
import com.akashkumar.unu.Utilities.Enums.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.akashkumar.unu.Utilities.Urls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(Urls.Baseurl+"/admin")
public class AdminController implements AdminResponse {
    @Autowired AdminServices adminServices;
    @Autowired ForgotPasswordService service;
    @Autowired AdminRepository adminRepository;
    @Autowired CourierRepository courierRepository;
    @Autowired UsersRepository usersRepository;
    @Autowired DealerRepository dealerRepository;

    @Override
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(AdminDto adminDto) {
            if (adminDto.getRole().equals(Role.ADMIN)){
                AdminDto savedAdmin = adminServices.registerAdmin(adminDto);
                ApiResponse<AdminDto> response = new ApiResponse<AdminDto>("Admin Register Successfully", savedAdmin);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);

            }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");

    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(LoginRequest loginRequest) {
        Optional<Admin> checkAdmin = adminRepository.findByMobile(loginRequest.getMobile());
        if (checkAdmin.isEmpty()){
            ApiResponse<LoginRequest> response = new ApiResponse<>("User Not Found", loginRequest);
            return ResponseEntity.status(404).body(response);
        }
        Admin getAdminData = checkAdmin.get();
        if(getAdminData.getMobile().equals(loginRequest.getMobile()) && getAdminData.getPassword().equals(loginRequest.getPassword())){

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAdminId(getAdminData.getAdminId());
            loginResponse.setName(getAdminData.getName());
            loginResponse.setEmail(getAdminData.getEmail());
            loginResponse.setMobile(getAdminData.getMobile());
            loginResponse.setRole(getAdminData.getRole());

            // ✅ Return only selected fields
            ApiResponse<LoginResponse> response = new ApiResponse<>("Login Successful", loginResponse);
            return ResponseEntity.ok(response);

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
        }

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam Role role, @RequestParam String email) {
        return ResponseEntity.ok(service.requestToSendRestLink(role, email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam Role role,
            @RequestParam String otp,
            @RequestParam String newPassword
    ) {
        return ResponseEntity.ok(service.requestToResetPassword(role,newPassword, otp));
    }

    @Override
    @PutMapping("/blockDealer")
    public ResponseEntity<?> blockDealer(@RequestBody BlockDealerRequest request) {
        Optional<Admin> checkAdmin = adminRepository.findByRole(Role.ADMIN);
        Optional<Dealer> checkDealer = dealerRepository.findById(request.getDealerId());

        if (checkAdmin.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }
        if (checkDealer.isEmpty()) {
            throw new RuntimeException("Dealer not found.");
        }

        Dealer dealer = checkDealer.get();
        Admin admin = checkAdmin.get();

        boolean wasBlocked = dealer.isBlock();
        dealer.setBlock(!wasBlocked);
        dealerRepository.save(dealer);

        List<String> blockedList = admin.getBlockedDealer();

        if (!wasBlocked) {
            if (!blockedList.contains(dealer.getDealerId())) {
                blockedList.add(dealer.getDealerId());
            }
        } else {
            blockedList.remove(dealer.getDealerId());
        }

        admin.setBlockedDealer(blockedList);
        adminRepository.save(admin);

        String message = !wasBlocked ? "Dealer blocked successfully" : "Dealer unblocked successfully";
        ApiResponse<?> apiResponse = new ApiResponse<>(message, dealer);

        return ResponseEntity.ok(apiResponse);
    }


    @Override
    @PutMapping("/blockCourier")
    public ResponseEntity<?> blockCourier(BlockDealerRequest request) {
        Optional<Admin> checkAdmin = adminRepository.findByRole(Role.ADMIN);
        Optional<Courier> checkCourier = courierRepository.findById(request.getCourierId());

        if (checkAdmin.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }
        if (checkCourier.isEmpty()) {
            throw new RuntimeException("Courier not found.");
        }

        Courier courier = checkCourier.get();
        Admin admin = checkAdmin.get();

        boolean wasBlocked = courier.isBlock();
        courier.setBlock(!wasBlocked);
        courierRepository.save(courier);

        List<String> blockedList = admin.getBlockedCourier();

        if (!wasBlocked) {
            if (!blockedList.contains(courier.getCourierId())) {
                blockedList.add(courier.getCourierId());
            }
        } else {
            blockedList.remove(courier.getCourierId());
        }

        admin.setBlockedCourier(blockedList);
        adminRepository.save(admin);

        String message = !wasBlocked ? "Courier blocked successfully" : "Courier unblocked successfully";
        ApiResponse<?> apiResponse = new ApiResponse<>(message, courier);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    @PutMapping("/blockUser")
    public ResponseEntity<?> blockUser(BlockDealerRequest request) {
        Optional<Admin> checkAdmin = adminRepository.findByRole(Role.ADMIN);
        Optional<Users> checkUser = usersRepository.findById(request.getUserId());

        if (checkAdmin.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }
        if (checkUser.isEmpty()) {
            throw new RuntimeException("User not found.");
        }

        Users users = checkUser.get();
        Admin admin = checkAdmin.get();

        boolean wasBlocked = users.isBlock();
        users.setBlock(!wasBlocked);
        usersRepository.save(users);

        List<String> blockedList = admin.getBlockedUser();

        if (!wasBlocked) {
            if (!blockedList.contains(users.getUserId())) {
                blockedList.add(users.getUserId());
            }
        } else {
            blockedList.remove(users.getUserId());
        }

        admin.setBlockedUser(blockedList);
        adminRepository.save(admin);

        String message = !wasBlocked ? "User blocked successfully" : "User unblocked successfully";
        ApiResponse<?> apiResponse = new ApiResponse<>(message, users);

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    @GetMapping("/getAllDealers")
    public ResponseEntity<?> getAllDealers() {
        Optional<Admin> me = adminRepository.findByRole(Role.ADMIN);
        if (me.isEmpty()){
            throw new RuntimeException("Admin Not Found ");
        }
        Admin admin = me.get();

        List<DealerDto> dealerList = new ArrayList<>();

        for (String ids : admin.getDealers()){
            Optional<Dealer> dealer = dealerRepository.findById(ids);
            if (dealer.isEmpty()){
                throw new RuntimeException("Dealer Not Found");
            }
            Dealer dealerData = dealer.get();
            DealerDto dto = DealerMapper.toDto(dealerData);
            dealerList.add(dto);
        }

        ApiResponse<?> apiResponse = new ApiResponse<>("All Dealers", dealerList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @GetMapping("/getAllBlockedDealers")
    public ResponseEntity<?> getAllBlockDealers() {
        Optional<Admin> me = adminRepository.findByRole(Role.ADMIN);
        if (me.isEmpty()){
            throw new RuntimeException("Admin Not Found ");
        }
        Admin admin = me.get();

        List<DealerDto> dealerList = new ArrayList<>();

        for (String ids : admin.getBlockedDealer()){
            Optional<Dealer> dealer = dealerRepository.findById(ids);
            if (dealer.isEmpty()){
                throw new RuntimeException("Dealer Not Found");
            }
            Dealer dealerData = dealer.get();
            DealerDto dto = DealerMapper.toDto(dealerData);
            dealerList.add(dto);
        }

        ApiResponse<?> apiResponse = new ApiResponse<>("All Blocked Dealers", dealerList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        Optional<Admin> me = adminRepository.findByRole(Role.ADMIN);
        if (me.isEmpty()){
            throw new RuntimeException("Admin Not Found ");
        }
        Admin admin = me.get();

        List<UsersDto> userList = new ArrayList<>();

        for (String ids : admin.getUsers()){
            Optional<Users> users = usersRepository.findById(ids);
            if (users.isEmpty()){
                throw new RuntimeException("User Not Found");
            }
            Users userdata = users.get();
            UsersDto dto = UsersMapper.toDto(userdata);
            userList.add(dto);
        }

        ApiResponse<?> apiResponse = new ApiResponse<>("All Users", userList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @Override
    @GetMapping("/getAllBlockUsers")
    public ResponseEntity<?> getAllBlockUsers() {
        Optional<Admin> me = adminRepository.findByRole(Role.ADMIN);
        if (me.isEmpty()){
            throw new RuntimeException("Admin Not Found ");
        }
        Admin admin = me.get();

        List<UsersDto> userList = new ArrayList<>();

        for (String ids : admin.getBlockedUser()){
            Optional<Users> users = usersRepository.findById(ids);
            if (users.isEmpty()){
                throw new RuntimeException("User Not Found");
            }
            Users userdata = users.get();
            UsersDto dto = UsersMapper.toDto(userdata);
            userList.add(dto);
        }

        ApiResponse<?> apiResponse = new ApiResponse<>("All Blocked Users", userList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @Override
    @GetMapping("/getAllCourier")
    public ResponseEntity<?> getAllCourier() {
        Optional<Admin> me = adminRepository.findByRole(Role.ADMIN);
        if (me.isEmpty()){
            throw new RuntimeException("Admin Not Found ");
        }
        Admin admin = me.get();

        List<CourierDto> courierList = new ArrayList<>();

        for (String ids : admin.getCouriers()){
            Optional<Courier> courier = courierRepository.findById(ids);
            if (courier.isEmpty()){
                throw new RuntimeException("Courier Not Found");
            }
            Courier courierData = courier.get();
            CourierDto dto = CourierMapper.toDto(courierData);
            courierList.add(dto);
        }

        ApiResponse<?> apiResponse = new ApiResponse<>("All Courier", courierList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @GetMapping("/getAllBlockCourier")
    public ResponseEntity<?> getAllBlockCourier() {
        Optional<Admin> me = adminRepository.findByRole(Role.ADMIN);
        if (me.isEmpty()){
            throw new RuntimeException("Admin Not Found ");
        }
        Admin admin = me.get();

        List<CourierDto> courierList = new ArrayList<>();

        for (String ids : admin.getBlockedCourier()){
            Optional<Courier> courier = courierRepository.findById(ids);
            if (courier.isEmpty()){
                throw new RuntimeException("Courier Not Found");
            }
            Courier courierData = courier.get();
            CourierDto dto = CourierMapper.toDto(courierData);
            courierList.add(dto);
        }

        ApiResponse<?> apiResponse = new ApiResponse<>("All Blocked Courier", courierList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
interface AdminResponse {
    ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminDto adminDto);
    ResponseEntity<?> loginAccount(@RequestBody LoginRequest loginRequest);
    ResponseEntity<?> blockDealer(@RequestBody BlockDealerRequest request);
    ResponseEntity<?> blockCourier(@RequestBody BlockDealerRequest request);
    ResponseEntity<?> blockUser(@RequestBody BlockDealerRequest request);

    ResponseEntity<?> getAllDealers();
    ResponseEntity<?> getAllBlockDealers();

    ResponseEntity<?> getAllUsers();
    ResponseEntity<?> getAllBlockUsers();

    ResponseEntity<?> getAllCourier();
    ResponseEntity<?> getAllBlockCourier();
}
