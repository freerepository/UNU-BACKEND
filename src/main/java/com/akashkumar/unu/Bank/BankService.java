package com.akashkumar.unu.Bank;

import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DealerRepository dealerRepository;
    @Autowired
    CourierRepository courierRepository;



    BankDto bankDetail(BankDto bankDto){
        Bank bank = null;
        if (!bankDto.getAdminId().isEmpty()){
            Optional<Admin> checkAdminBank = adminRepository.findById(bankDto.getAdminId());
            if (checkAdminBank.isEmpty()){
                throw new UserNotFound("Admin Not Found ");
            }
            Admin admin = checkAdminBank.get();
            if (admin.isCheckBank()){
               throw new RuntimeException("Bank Detail Already Available");
            }else{

                bank = BankMapper.toEntity(bankDto);
                bank.setUserId(null);
                bank.setCourierId(null); // YE FIELD ADD NAHI HONGE
                bank.setDealerId(null);
                admin.setBankDetail(bank);
                bankRepository.save(bank);

                admin.setCheckBank(true);
                adminRepository.save(admin);

            }
        }else if (!bankDto.getUserId().isEmpty()){
            Optional<Users> checkUsersBank = usersRepository.findById(bankDto.getUserId());
            if (checkUsersBank.isEmpty()){
                throw new UserNotFound("User Not Found ");
            }
            Users user = checkUsersBank.get();
            if (user.isCheckBank()){
                throw new RuntimeException("Bank Detail Already Available");
            }else{

                bank = BankMapper.toEntity(bankDto);
                bank.setAdminId(null);
                bank.setCourierId(null);
                bank.setDealerId(null);
                user.setBankDetail(bank);
                bankRepository.save(bank);

                user.setCheckBank(true);
                usersRepository.save(user);

            }


        }else if (!bankDto.getCourierId().isEmpty()){
            Optional<Courier> checkCourier = courierRepository.findById(bankDto.getCourierId());
            if (checkCourier.isEmpty()){
                throw new UserNotFound("Courier Not Found ");
            }
            Courier courier = checkCourier.get();
            if (courier.isCheckBank()){
                throw new RuntimeException("Bank Detail Already Available");
            }else{

                bank = BankMapper.toEntity(bankDto);
                bank.setUserId(null);
                bank.setAdminId(null); // YE FIELD ADD NAHI HONGE
                bank.setDealerId(null);
                courier.setBankDetail(bank);
                bankRepository.save(bank);

                courier.setCheckBank(true);
                courierRepository.save(courier);

            }

        }else{
           //dealer id
            Optional<Dealer> checkDealer = dealerRepository.findById(bankDto.getDealerId());
            if (checkDealer.isEmpty()){
                throw new UserNotFound("Dealer Not Found ");
            }
            Dealer dealer = checkDealer.get();
            if (dealer.isCheckBank()){
                throw new RuntimeException("Bank Detail Already Available");
            }else{

                bank = BankMapper.toEntity(bankDto);
                bank.setUserId(null);
                bank.setCourierId(null); // YE FIELD ADD NAHI HONGE
                bank.setAdminId(null);
                dealer.setBankDetail(bank);
                bankRepository.save(bank);

                dealer.setCheckBank(true);
                dealerRepository.save(dealer);
            }
        }
        return BankMapper.toDto(bank);
    }
}
