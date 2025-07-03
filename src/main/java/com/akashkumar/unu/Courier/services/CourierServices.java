package com.akashkumar.unu.Courier.services;

import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Courier.dto.CourierDto;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.mapper.CourierMapper;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserFound;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourierServices implements CourierService{
    @Autowired
    CourierRepository courierRepository;

    @Autowired
    AdminRepository adminRepository;

    @Override
    public CourierDto createCourier(CourierDto courierDto) {
        Optional<Admin> checkAdmin = adminRepository.findByRole(Role.ADMIN);
        Optional<Courier> checkCourier = courierRepository.findByMobile(courierDto.getMobile());
        if (checkAdmin.isEmpty()){
            throw new UserNotFound("Admin Not Found ");
        }
        Admin admin = checkAdmin.get();
        Courier courier = CourierMapper.toEntity(courierDto);
        if (checkCourier.isEmpty()){
            if (courierDto.getBankDetail()==null){
                courierDto.setBankDetail(null);
            }
            if (courierDto.getCourierAddress()==null){
                courierDto.setCourierAddress(null);
            }
            courierRepository.save(courier);
            admin.getCouriers().add(courier.getCourierId());
            admin.getActiveCouriers().add(courier.getCourierId());
        }else{
            throw new UserFound("Courier already exists ");
        }
        adminRepository.save(admin);
        return CourierMapper.toDto(courier);
    }
}
interface CourierService{
    CourierDto createCourier(CourierDto courierDto);
}
