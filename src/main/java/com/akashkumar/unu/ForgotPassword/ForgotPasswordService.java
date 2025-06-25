package com.akashkumar.unu.ForgotPassword;


import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.repository.UsersRepository;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class ForgotPasswordService implements ForgotPasswordServices {

    @Autowired
    UsersRepository userRepository;

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    CourierRepository courierRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    EmailService emailService;

    @Override
    public String requestToSendRestLink(Role role, String email) {

        if (role.equals(Role.ADMIN)){
            Optional<Admin> checkAdmin = adminRepository.findByAdminEmail(email);
            if (checkAdmin.isPresent()){
                Admin adminDetails = checkAdmin.get();
                int otpGen = otpGenrator();
                String otp = String.valueOf(otpGen);
                adminDetails.setOtp(otp);
                adminDetails.setOtpExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 5));
                adminRepository.save(adminDetails);
                emailService.sendResetToken(email, otp);
                return "admin";
            }else{
                throw new UserNotFound("Admin Not Found");
            }

        }else if (role.equals(Role.COURIER)){
            Optional<Courier> checkCourier = courierRepository.findByCourierEmail(email);
            if (checkCourier.isPresent()){
                Courier courierDetails = checkCourier.get();
                int otpGen = otpGenrator();
                String otp = String.valueOf(otpGen);
                courierDetails.setOtp(otp);
                courierDetails.setOtpExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 5));
                courierRepository.save(courierDetails);
                emailService.sendResetToken(email, otp);
                return "courier";
            }else{
                throw new UserNotFound("Courier Not Found");
            }
        }else if(role.equals(Role.DEALER)){
            Optional<Dealer> checkDealer = dealerRepository.findByDealerEmail(email);
            if (checkDealer.isPresent()){
                Dealer dealerDetails = checkDealer.get();
                int otpGen = otpGenrator();
                String otp = String.valueOf(otpGen);
                dealerDetails.setOtp(otp);
                dealerDetails.setOtpExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 5));
                dealerRepository.save(dealerDetails);
                emailService.sendResetToken(email, otp);
                return "dealer";
            }else{
                throw new UserNotFound("Dealer Not Found");
            }
        }else{
            //user
            Optional<Users> checkUser = userRepository.findByUserEmail(email);
            if (checkUser.isPresent()){
                Users userDetails = checkUser.get();
                int otpGen = otpGenrator();
                String otp = String.valueOf(otpGen);
                userDetails.setOtp(otp);
                userDetails.setOtpExpiry(new Date(System.currentTimeMillis() + 1000 * 60 * 5));
                userRepository.save(userDetails);
                emailService.sendResetToken(email, otp);
                return "user";
            }else{
                throw new UserNotFound("User Not Found");
            }
        }
    }

    private int otpGenrator() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(6) + 1;
            otp.append(digit);
        }
        return Integer.parseInt(otp.toString());
    }


    @Override
    public String requestToResetPassword(Role role, String newPassword, String otp) {
        if (role.equals(Role.ADMIN)){
            Optional<Admin> checkAdmin = adminRepository.findByOtp(otp);
            if (checkAdmin.isPresent()) {
                Admin admin = checkAdmin.get();
                if (admin.getOtpExpiry().after(new Date())) {
                    admin.setAdminPassword(newPassword);
                    admin.setOtp(null);
                    admin.setOtpExpiry(null);
                    adminRepository.save(admin);
                    return "Admin password Updated successfully ";
                } else {
                    return "Token Expired";
                }
            } else {
                return "Invalid Token";
            }

        }else if (role.equals(Role.COURIER)){
            Optional<Courier> checkCourier = courierRepository.findByOtp(otp);
            if (checkCourier.isPresent()) {
                Courier courier = checkCourier.get();
                if (courier.getOtpExpiry().after(new Date())) {
                    courier.setCourierPassword(newPassword);
                    courier.setOtp(null);
                    courier.setOtpExpiry(null);
                    courierRepository.save(courier);
                    return "Courier password Updated successfully ";
                } else {
                    return "Token Expired";
                }
            } else {
                return "Invalid Token";
            }

        }else if(role.equals(Role.DEALER)){
            Optional<Dealer> checkDealer = dealerRepository.findByOtp(otp);
            if (checkDealer.isPresent()) {
                Dealer dealer = checkDealer.get();
                if (dealer.getOtpExpiry().after(new Date())) {
                    dealer.setDealerPassword(newPassword);
                    dealer.setOtp(null);
                    dealer.setOtpExpiry(null);
                    dealerRepository.save(dealer);
                    return "Dealer password Updated successfully ";
                } else {
                    return "Token Expired";
                }
            } else {
                return "Invalid Token";
            }
        }else {
            Optional<Users> userOptional = userRepository.findByOtp(otp);
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                if (user.getOtpExpiry().after(new Date())) {
                    user.setUserPassword(newPassword);
                    user.setOtp(null);
                    user.setOtpExpiry(null);
                    userRepository.save(user);
                    return "User password Updated successfully ";
                } else {
                    return "Token Expired";
                }
            } else {
                return "Invalid Token";
            }
        }
    }
}
interface ForgotPasswordServices {
    String requestToSendRestLink(Role role, String email);
    String requestToResetPassword(Role role, String newPassword, String otp);
}

