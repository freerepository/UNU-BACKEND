package com.akashkumar.unu.User.mapper;

import com.akashkumar.unu.User.dto.UsersDto;
import com.akashkumar.unu.User.entity.Users;

import java.util.Date;

public class UsersMapper {

    public static Users toEntity(UsersDto dto) {
        Users user = new Users();
        user.setUserId(dto.getUserId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setMobile(dto.getMobile());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        user.setActive(dto.isActive());
        user.setBlock(dto.isBlock());
        user.setCheckBank(dto.isCheckBank());
        user.setCheckAddress(dto.isCheckAddress());
        user.setTotalSpend(dto.getTotalSpend());

        user.setAddress(dto.getAddress());
        user.setBankDetail(dto.getBankDetail());
        user.setCarts(dto.getCarts());
        user.setOrders(dto.getOrders());
        user.setOrderedHistory(dto.getOrderedHistory());

        user.setOtp(dto.getOtp());
        user.setOtpExpiry(dto.getOtpExpiry());

        return user;
    }

    public static UsersDto toDto(Users entity) {
        UsersDto dto = new UsersDto();
        dto.setUserId(entity.getUserId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setMobile(entity.getMobile());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());

        dto.setActive(entity.isActive());
        dto.setBlock(entity.isBlock());
        dto.setCheckBank(entity.isCheckBank());
        dto.setCheckAddress(entity.isCheckAddress());
        dto.setTotalSpend(entity.getTotalSpend());

        dto.setAddress(entity.getAddress());
        dto.setBankDetail(entity.getBankDetail());
        dto.setCarts(entity.getCarts());
        dto.setOrders(entity.getOrders());
        dto.setOrderedHistory(entity.getOrderedHistory());
        dto.setOtp(entity.getOtp());
        dto.setOtpExpiry(entity.getOtpExpiry());
        return dto;
    }
}

