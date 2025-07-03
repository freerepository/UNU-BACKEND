package com.akashkumar.unu.Courier.mapper;

import com.akashkumar.unu.Courier.dto.CourierDto;
import com.akashkumar.unu.Courier.entity.Courier;

public class CourierMapper {

        public static Courier toEntity(CourierDto dto) {
            Courier courier = new Courier();

            courier.setCourierId(dto.getCourierId());
            courier.setName(dto.getName());
            courier.setEmail(dto.getEmail());
            courier.setMobile(dto.getMobile());
            courier.setAdharNumber(dto.getAdharNumber());
//            courier.setDealerName(dto.getDealerName());
            courier.setRole(dto.getRole());
            courier.setPanNumber(dto.getPanNumber());
            courier.setPassword(dto.getPassword());

            courier.setCourierCity(dto.getCourierCity());

            courier.setTotalEarning(dto.getTotalEarning());
            courier.setActive(dto.isActive());
            courier.setBlock(dto.isBlock());
            courier.setCheckBank(dto.isCheckBank());
            courier.setCheckAddress(dto.isCheckAddress());

            courier.setBankDetail(dto.getBankDetail());
            courier.setCourierAddress(dto.getCourierAddress());

            courier.setDealers(dto.getDealers());
            courier.setOrdersList(dto.getOrdersList());
            courier.setConfirmOrdersIds(dto.getConfirmOrdersIds());
            courier.setOutOfDelivery(dto.getOutOfDelivery());
            courier.setDelivered(dto.getDelivered());
            courier.setOtp(dto.getOtp());
            courier.setOtpExpiry(dto.getOtpExpiry());


            return courier;
        }

        public static CourierDto toDto(Courier courier) {
            CourierDto dto = new CourierDto();

            dto.setCourierId(courier.getCourierId());
            dto.setName(courier.getName());
            dto.setEmail(courier.getEmail());
            dto.setMobile(courier.getMobile());
            dto.setAdharNumber(courier.getAdharNumber());
//            dto.setDealerName(courier.getDealerName());
            dto.setRole(courier.getRole());
            dto.setPanNumber(courier.getPanNumber());
            dto.setPassword(courier.getPassword());

            dto.setCourierCity(courier.getCourierCity());

            dto.setTotalEarning(courier.getTotalEarning());
            dto.setActive(courier.isActive());
            dto.setBlock(courier.isBlock());
            dto.setCheckBank(courier.isCheckBank());
            dto.setCheckAddress(courier.isCheckAddress());

            dto.setBankDetail(courier.getBankDetail());
            dto.setCourierAddress(courier.getCourierAddress());
            dto.setOtp(courier.getOtp());
            dto.setOtpExpiry(courier.getOtpExpiry());
            dto.setDealers(courier.getDealers());
            dto.setOrdersList(courier.getOrdersList());
            dto.setConfirmOrdersIds(courier.getConfirmOrdersIds());
            dto.setOutOfDelivery(courier.getOutOfDelivery());
            dto.setDelivered(courier.getDelivered());

            return dto;
        }
    }

