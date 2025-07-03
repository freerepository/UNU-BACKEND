package com.akashkumar.unu.Courier.repository;

import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends MongoRepository<Courier, String> {
    Optional<Courier> findByMobile(String courierMobile);
    @Query("{ 'courierAddress.userCity': ?0 }")
    Optional<Courier> findByCourierCity(String courierCity);
    Optional<Courier> findByRole(Role role);
    Optional<Courier> findByEmail(String email);
    Optional<Courier> findByOtp(String token);
}
