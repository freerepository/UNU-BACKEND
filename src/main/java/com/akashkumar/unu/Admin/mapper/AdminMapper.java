package com.akashkumar.unu.Admin.mapper;

import com.akashkumar.unu.Admin.dto.AdminDto;
import com.akashkumar.unu.Admin.entity.Admin;

public class AdminMapper {
    public static AdminDto toDto(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setAdminId(admin.getAdminId());
        dto.setName(admin.getName());
        dto.setEmail(admin.getEmail());
        dto.setMobile(admin.getMobile());
        dto.setPassword(admin.getPassword());
        dto.setTotalEarning(admin.getTotalEarning());
        dto.setRole(admin.getRole());
        dto.setBankDetail(admin.getBankDetail());

        dto.setCheckBank(admin.isCheckBank());

        dto.setUsers(admin.getUsers());
        dto.setDealers(admin.getDealers());
        dto.setCouriers(admin.getCouriers());

        dto.setBlockedUser(admin.getBlockedUser());
        dto.setBlockedCourier(admin.getBlockedCourier());
        dto.setBlockedDealer(admin.getBlockedDealer());

        dto.setActiveUsers(admin.getActiveUsers());
        dto.setActiveDealers(admin.getActiveDealers());
        dto.setActiveCouriers(admin.getActiveCouriers());

        dto.setOtp(admin.getOtp());
        dto.setOtpExpiry(admin.getOtpExpiry());

        return dto;
    }

    public static Admin toEntity(AdminDto dto) {
        Admin admin = new Admin();

        admin.setAdminId(dto.getAdminId());
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setMobile(dto.getMobile());
        admin.setPassword(dto.getPassword());
        admin.setTotalEarning(dto.getTotalEarning());
        admin.setRole(dto.getRole());
        admin.setBankDetail(dto.getBankDetail());

        admin.setCheckBank(dto.isCheckBank());

        admin.setUsers(dto.getUsers());
        admin.setDealers(dto.getDealers());
        admin.setCouriers(dto.getCouriers());

        admin.setActiveUsers(dto.getActiveUsers());
        admin.setActiveDealers(dto.getActiveDealers());
        admin.setActiveCouriers(dto.getActiveCouriers());

        admin.setOtp(dto.getOtp());
        admin.setOtpExpiry(dto.getOtpExpiry());

        return admin;
    }
}
