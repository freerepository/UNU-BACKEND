package com.akashkumar.unu.Address;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AddressRepository extends MongoRepository<MyAddress, String> {
    Optional<MyAddress> findByUserCity(String cityName);
}
