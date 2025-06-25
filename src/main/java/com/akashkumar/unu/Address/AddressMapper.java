package com.akashkumar.unu.Address;

public class AddressMapper {
    public static MyAddress toEntity(MyAddressDto dto) {
        MyAddress address = new MyAddress();
        address.setMyAddressId(dto.getMyAddressId());

        address.setUserId(dto.getUserId());
        address.setCourierId(dto.getCourierId());
        address.setDealerId(dto.getDealerId());

        address.setUsername(dto.getUsername());
        address.setUserCity(dto.getUserCity());
        address.setUserState(dto.getUserState());
        address.setUserPincode(dto.getUserPincode());
        return address;
    }

    public static MyAddressDto toDto(MyAddress address) {
        MyAddressDto dto = new MyAddressDto();
        dto.setMyAddressId(address.getMyAddressId());

        dto.setUserId(address.getUserId());
        dto.setCourierId(address.getCourierId());
        dto.setDealerId(address.getDealerId());

        dto.setUsername(address.getUsername());
        dto.setUserCity(address.getUserCity());
        dto.setUserState(address.getUserState());
        dto.setUserPincode(address.getUserPincode());
        return dto;
    }
}
