package com.akashkumar.unu.Address;

import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService{
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    DealerRepository dealerRepository;
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    AdminRepository adminRepository;

    public MyAddressDto addressDetail(MyAddressDto addressDto) {
        MyAddress address = null;

        if (addressDto.getUserId() != null && !addressDto.getUserId().isEmpty()) {
            Optional<Users> checkUser = usersRepository.findById(addressDto.getUserId());
            if (checkUser.isEmpty()) throw new UserNotFound("User Not Found");

            Users user = checkUser.get();
            if (user.isCheckAddress()) throw new RuntimeException("Address Already Present");

            address = AddressMapper.toEntity(addressDto);
            address.setDealerId(null);
            address.setCourierId(null);

//            user.getAddress().add(Collections.singletonList(address).toString());
            user.setAddress(address);
            user.setCheckAddress(true);

            addressRepository.save(address);
            usersRepository.save(user);

        } else if (addressDto.getCourierId() != null && !addressDto.getCourierId().isEmpty()) {
            Optional<Courier> checkCourier = courierRepository.findById(addressDto.getCourierId());
            if (checkCourier.isEmpty()) throw new UserNotFound("Courier Not Found");

            Courier courier = checkCourier.get();
            if (courier.isCheckAddress()) throw new RuntimeException("Address Already Present");

            address = AddressMapper.toEntity(addressDto);
            address.setUserId(null);
            address.setDealerId(null);

            courier.setCourierAddress(address);
            courier.setCheckAddress(true);

            addressRepository.save(address);
            courierRepository.save(courier);
        } else {
            // dealer
            assert addressDto.getDealerId() != null;
            Optional<Dealer> checkDealer = dealerRepository.findById(addressDto.getDealerId());
            if (checkDealer.isEmpty()) throw new UserNotFound("Courier Not Found");

            Dealer dealer = checkDealer.get();
            if (dealer.isCheckAddress()) throw new RuntimeException("Address Already Present");

            address = AddressMapper.toEntity(addressDto);
            address.setUserId(null);
            address.setCourierId(null);

            dealer.setMyAddress(address);
            dealer.setCheckAddress(true);

            addressRepository.save(address);
            dealerRepository.save(dealer);
        }

        return AddressMapper.toDto(address);
    }

}

