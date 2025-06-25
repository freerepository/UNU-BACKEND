package com.akashkumar.unu.Admin.mapper;

import com.akashkumar.unu.Admin.dto.AdminDto;
import com.akashkumar.unu.Admin.entity.Admin;

public class AdminMapper {
    public static AdminDto toDto(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setAdminId(admin.getAdminId());
        dto.setAdminName(admin.getAdminName());
        dto.setAdminEmail(admin.getAdminEmail());
        dto.setAdminMobile(admin.getAdminMobile());
        dto.setAdminPassword(admin.getAdminPassword());
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
        admin.setAdminName(dto.getAdminName());
        admin.setAdminEmail(dto.getAdminEmail());
        admin.setAdminMobile(dto.getAdminMobile());
        admin.setAdminPassword(dto.getAdminPassword());
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
