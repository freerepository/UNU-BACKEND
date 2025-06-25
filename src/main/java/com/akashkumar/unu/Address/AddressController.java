package com.akashkumar.unu.Address;

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
public class AddressController implements AddressServices{

    @Autowired
    AddressService addressServices;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    CourierRepository courierRepository;



    @Override
    @PostMapping("/add-address")
    public ResponseEntity<?> addAddress(MyAddressDto addressDto) {
        MyAddressDto addressDTO = addressServices.addressDetail(addressDto);
        if (!addressDTO.getUserId().isEmpty()){
            ApiResponse<MyAddressDto> apiResponse = new ApiResponse<>("User Address Added Successfully ", addressDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else if(!addressDTO.getDealerId().isEmpty()){
            ApiResponse<MyAddressDto> apiResponse = new ApiResponse<>("Dealer Address Added Successfully ", addressDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else{
            ApiResponse<MyAddressDto> apiResponse = new ApiResponse<>("Courier Address Added Successfully ", addressDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }

    @Override
    @PutMapping("/update-address")
    public ResponseEntity<?> updateAddress(MyAddressDto addressDto) {
//        MyAddressDto addressDTO = addressServices.addressDetail(addressDto);
        if (!addressDto.getUserId().isEmpty()){

            Optional<Users> checkUser = usersRepository.findById(addressDto.getUserId());
            if (checkUser.isEmpty()){
                throw new RuntimeException("User Not Found ");
            }

            Users users = checkUser.get();
            String addressId = users.getAddress().getMyAddressId();
            Optional<MyAddress> checkAddress = addressRepository.findById(addressId);
            if (checkAddress.isEmpty()){
                throw new RuntimeException("Address Not Found");
            }
            //first of all delete your current id
            addressRepository.deleteById(addressId);

            MyAddress address = checkAddress.get();
            address.setUsername(addressDto.getUsername());
            address.setUserCity(addressDto.getUserCity());
            address.setUserState(addressDto.getUserState());
            address.setUserPincode(addressDto.getUserPincode());
            addressRepository.save(address);

            users.setAddress(address);
            usersRepository.save(users);

            ApiResponse<MyAddress> apiResponse = new ApiResponse<>("User Address Updated Successfully ", address);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);


        }else if(!addressDto.getDealerId().isEmpty()){

            Optional<Dealer> checkDealer = dealerRepository.findById(addressDto.getDealerId());
            if (checkDealer.isEmpty()){
                throw new RuntimeException("Dealer Not Found ");
            }

            Dealer dealer = checkDealer.get();
            String addressId = dealer.getMyAddress().getMyAddressId();
            Optional<MyAddress> checkAddress = addressRepository.findById(addressId);
            if (checkAddress.isEmpty()){
                throw new RuntimeException("Address Not Found");
            }
            //first of all delete your current id
            addressRepository.deleteById(addressId);

            MyAddress address = checkAddress.get();
            address.setUsername(addressDto.getUsername());
            address.setUserCity(addressDto.getUserCity());
            address.setUserState(addressDto.getUserState());
            address.setUserPincode(addressDto.getUserPincode());
            addressRepository.save(address);

            dealer.setMyAddress(address);
            dealerRepository.save(dealer);

            ApiResponse<MyAddress > apiResponse = new ApiResponse<>("Dealer Address Updated Successfully ", address);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else{
            Optional<Courier> checkCourier = courierRepository.findById(addressDto.getCourierId());
            if (checkCourier.isEmpty()){
                throw new RuntimeException("Courier Not Found ");
            }

            Courier courier = checkCourier.get();
            String addressId = courier.getCourierAddress().getMyAddressId();
            Optional<MyAddress> checkAddress = addressRepository.findById(addressId);
            if (checkAddress.isEmpty()){
                throw new RuntimeException("Address Not Found");
            }
            //first of all delete your current id
            addressRepository.deleteById(addressId);

            MyAddress address = checkAddress.get();
            address.setUsername(addressDto.getUsername());
            address.setUserCity(addressDto.getUserCity());
            address.setUserState(addressDto.getUserState());
            address.setUserPincode(addressDto.getUserPincode());
            addressRepository.save(address);

            courier.setCourierAddress(address);
            courierRepository.save(courier);

            ApiResponse<MyAddress > apiResponse = new ApiResponse<>("Courier Address Updated Successfully ", address);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }
}
interface AddressServices{
    ResponseEntity<?> addAddress(@RequestBody MyAddressDto addressDto);
    ResponseEntity<?> updateAddress(@RequestBody MyAddressDto addressDto);
}
