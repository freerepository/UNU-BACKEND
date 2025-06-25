package com.akashkumar.unu.Dealer.mapper;

import com.akashkumar.unu.Dealer.dto.DealerDto;
import com.akashkumar.unu.Dealer.entity.Dealer;

public class DealerMapper {

    public static DealerDto toDto(Dealer dealer) {
        DealerDto dto = new DealerDto();
        dto.setDealerId(dealer.getDealerId());
        dto.setDealerName(dealer.getDealerName());
        dto.setDealerEmail(dealer.getDealerEmail());
        dto.setDealerMobile(dealer.getDealerMobile());
        dto.setDealerPassword(dealer.getDealerPassword());
        dto.setRole(dealer.getRole());
        dto.setBankDetail(dealer.getBankDetail());
        dto.setMyAddress(dealer.getMyAddress());
        dto.setActive(dealer.isActive());
        dto.setBlock(dealer.isBlock());
        dto.setCheckBank(dealer.isCheckBank());
        dto.setCheckAddress(dealer.isCheckAddress());
        dto.setTotalEarning(dealer.getTotalEarning());

        dto.setAllProductsIds(dealer.getAllProductsIds());
        dto.setCategoriesIds(dealer.getCategoriesIds());
        dto.setSubCategoriesIds(dealer.getSubCategoriesIds());
        dto.setUsersIds(dealer.getUsersIds());
        dto.setCouriersIds(dealer.getCouriersIds());

        dto.setOrdersIds(dealer.getOrdersIds());
        dto.setConfirmOrdersIds(dealer.getConfirmOrdersIds());
        dto.setShippedOrdersIds(dealer.getShippedOrdersIds());
        dto.setOutOfDeliveryOrdersIds(dealer.getOutOfDeliveryOrdersIds());
        dto.setDelivered(dealer.getDelivered());

        dto.setOtp(dealer.getOtp());
        dto.setOtpExpiry(dealer.getOtpExpiry());
        return dto;
    }

    public static Dealer toEntity(DealerDto dto) {
        Dealer dealer = new Dealer();
        dealer.setDealerId(dto.getDealerId());
        dealer.setDealerName(dto.getDealerName());
        dealer.setDealerEmail(dto.getDealerEmail());
        dealer.setDealerMobile(dto.getDealerMobile());
        dealer.setDealerPassword(dto.getDealerPassword());
        dealer.setRole(dto.getRole());
        dealer.setBankDetail(dto.getBankDetail());
        dealer.setMyAddress(dto.getMyAddress());
        dealer.setActive(dto.isActive());
        dealer.setBlock(dto.isBlock());
        dealer.setCheckBank(dto.isCheckBank());
        dealer.setTotalEarning(dto.getTotalEarning());

        dealer.setAllProductsIds(dto.getAllProductsIds());
        dealer.setCategoriesIds(dto.getCategoriesIds());
        dealer.setSubCategoriesIds(dto.getSubCategoriesIds());
        dealer.setUsersIds(dto.getUsersIds());
        dealer.setCheckAddress(dto.isCheckAddress());
        dealer.setCouriersIds(dto.getCouriersIds());

        dealer.setOrdersIds(dto.getOrdersIds());
        dealer.setConfirmOrdersIds(dto.getConfirmOrdersIds());
        dealer.setShippedOrdersIds(dto.getShippedOrdersIds());
        dealer.setOutOfDeliveryOrdersIds(dto.getOutOfDeliveryOrdersIds());
        dealer.setDelivered(dto.getDelivered());

        dealer.setOtp(dto.getOtp());
        dealer.setOtpExpiry(dto.getOtpExpiry());

        return dealer;
    }
}

