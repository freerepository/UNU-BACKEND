package com.akashkumar.unu.Bank;

import com.akashkumar.unu.Address.MyAddress;
import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(Urls.Baseurl)
public class BankController implements BankDetailInterface {

    @Autowired BankService bankService;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    CourierRepository courierRepository;

    @Autowired
    AdminRepository adminRepository;


    @Override
    @PostMapping("/bankDetail")
    public ResponseEntity<?> addBank(BankDto bankDto) {
        BankDto bankDTO = bankService.bankDetail(bankDto);
        if (!bankDTO.getAdminId().isEmpty()){
            ApiResponse<BankDto> apiResponse = new ApiResponse<>("Admin Bank Added Successfully ", bankDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else if(!bankDTO.getUserId().isEmpty()){
            ApiResponse<BankDto> apiResponse = new ApiResponse<>("User Bank Added Successfully ", bankDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else if(!bankDTO.getCourierId().isEmpty()){
            ApiResponse<BankDto> apiResponse = new ApiResponse<>("Courier Bank Added Successfully ", bankDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else{
            ApiResponse<BankDto> apiResponse = new ApiResponse<>("Dealer Bank Added Successfully ", bankDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }

    @Override
    @PutMapping("/update-bank")
    public ResponseEntity<?> updateBank(BankDto bankDto) {
        if (!bankDto.getAdminId().isEmpty()){
            Optional<Admin> checkAdmin = adminRepository.findById(bankDto.getAdminId());
            if (checkAdmin.isEmpty()){
                throw new RuntimeException("Admin Not Found : Check Your Database");
            }

            Admin admin = checkAdmin.get();
            String bankId = admin.getBankDetail().getBankId();
            Optional<Bank> checkBank = bankRepository.findById(bankId);
            if (checkBank.isEmpty()){
                throw new RuntimeException("Bank Not Found");
            }
            //first of all delete your current id
            bankRepository.deleteById(bankId);

            Bank bank = checkBank.get();
            bank.setAccountHolderName(bankDto.getAccountHolderName());
            bank.setBankName(bankDto.getBankName());
            bank.setAccountNumber(bankDto.getAccountNumber());
            bank.setIfscCode(bankDto.getIfscCode());
            bank.setBranchName(bankDto.getBranchName());
            bank.setAdharNumber(bankDto.getAdharNumber());
            bank.setPanNumber(bankDto.getPanNumber());
            bankRepository.save(bank);

            admin.setBankDetail(bank);
            adminRepository.save(admin);

            ApiResponse<Bank> apiResponse = new ApiResponse<>("Admin Bank Updated Successfully ", bank);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        }else if (!bankDto.getUserId().isEmpty()){

            Optional<Users> checkUser = usersRepository.findById(bankDto.getUserId());
            if (checkUser.isEmpty()){
                throw new RuntimeException("User Not Found ");
            }

            Users users = checkUser.get();
            String bankId = users.getBankDetail().getBankId();
            Optional<Bank> checkBank = bankRepository.findById(bankId);
            if (checkBank.isEmpty()){
                throw new RuntimeException("Bank Not Found");
            }
            //first of all delete your current id
            bankRepository.deleteById(bankId);

            Bank bank = checkBank.get();
            bank.setAccountHolderName(bankDto.getAccountHolderName());
            bank.setBankName(bankDto.getBankName());
            bank.setAccountNumber(bankDto.getAccountNumber());
            bank.setIfscCode(bankDto.getIfscCode());
            bank.setBranchName(bankDto.getBranchName());
            bank.setAdharNumber(bankDto.getAdharNumber());
            bank.setPanNumber(bankDto.getPanNumber());
            bankRepository.save(bank);

            users.setBankDetail(bank);
            usersRepository.save(users);

            ApiResponse<Bank> apiResponse = new ApiResponse<>("User Bank Updated Successfully ", bank);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);


        }else if(!bankDto.getDealerId().isEmpty()){

            Optional<Dealer> checkDealer = dealerRepository.findById(bankDto.getDealerId());
            if (checkDealer.isEmpty()){
                throw new RuntimeException("Dealer Not Found ");
            }

            Dealer dealer = checkDealer.get();
            String bankId = dealer.getBankDetail().getBankId();
            Optional<Bank> checkBank = bankRepository.findById(bankId);
            if (checkBank.isEmpty()){
                throw new RuntimeException("Bank Not Found");
            }
            //first of all delete your current id
            bankRepository.deleteById(bankId);

            Bank bank = checkBank.get();
            bank.setAccountHolderName(bankDto.getAccountHolderName());
            bank.setBankName(bankDto.getBankName());
            bank.setAccountNumber(bankDto.getAccountNumber());
            bank.setIfscCode(bankDto.getIfscCode());
            bank.setBranchName(bankDto.getBranchName());
            bank.setAdharNumber(bankDto.getAdharNumber());
            bank.setPanNumber(bankDto.getPanNumber());
            bankRepository.save(bank);

            dealer.setBankDetail(bank);
            dealerRepository.save(dealer);

            ApiResponse<Bank> apiResponse = new ApiResponse<>("Dealer Bank Updated Successfully ", bank);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else{
            Optional<Courier> checkCourier = courierRepository.findById(bankDto.getCourierId());
            if (checkCourier.isEmpty()){
                throw new RuntimeException("Courier Not Found ");
            }

            Courier courier = checkCourier.get();
            String bankId = courier.getBankDetail().getBankId();
            Optional<Bank> checkBank = bankRepository.findById(bankId);
            if (checkBank.isEmpty()){
                throw new RuntimeException("Bank Not Found");
            }
            //first of all delete your current id
            bankRepository.deleteById(bankId);

            Bank bank = checkBank.get();
            bank.setAccountHolderName(bankDto.getAccountHolderName());
            bank.setBankName(bankDto.getBankName());
            bank.setAccountNumber(bankDto.getAccountNumber());
            bank.setIfscCode(bankDto.getIfscCode());
            bank.setBranchName(bankDto.getBranchName());
            bank.setAdharNumber(bankDto.getAdharNumber());
            bank.setPanNumber(bankDto.getPanNumber());
            bankRepository.save(bank);

            courier.setBankDetail(bank);
            courierRepository.save(courier);

            ApiResponse<Bank> apiResponse = new ApiResponse<>("Courier Bank Updated Successfully ", bank);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }
}
interface BankDetailInterface{
    ResponseEntity<?> addBank(@RequestBody BankDto bankDto);
    ResponseEntity<?> updateBank(@RequestBody BankDto bankDto);
}
