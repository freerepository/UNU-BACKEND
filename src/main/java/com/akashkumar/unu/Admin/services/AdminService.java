package com.akashkumar.unu.Admin.services;

import com.akashkumar.unu.Admin.AdminServices.AdminServices;
import com.akashkumar.unu.Admin.dto.AdminDto;
import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.mapper.AdminMapper;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Bank.Bank;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserAlreadyExistException;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService implements AdminServices {

    @Autowired
    AdminRepository adminRepository;


    @Override
    public AdminDto registerAdmin(AdminDto adminDto) {
        //check other one have to submit with Role of Admin
        Optional<Admin> admins = adminRepository.findByRole(Role.ADMIN);
        if (admins.isPresent()) {
            throw new UserAlreadyExistException("Admin Already Exist with the name of "+adminDto.getName());
        }
        if (adminDto.getBankDetail() != null) {
            Bank bank = adminDto.getBankDetail();
            if (bank.getBankId() == null &&
                    bank.getUserId() == null &&
                    bank.getAccountHolderName() == null &&
                    bank.getBankName() == null &&
                    bank.getAccountNumber() == null &&
                    bank.getIfscCode() == null &&
                    bank.getBranchName() == null &&
                    bank.getAdharNumber() == null &&
                    bank.getPanNumber() == null) {

                adminDto.setBankDetail(null);
            }
        }
        Admin admin = AdminMapper.toEntity(adminDto);
        Admin savedAdmin = adminRepository.save(admin);
        return AdminMapper.toDto(savedAdmin);
    }
}
