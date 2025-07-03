package com.akashkumar.unu.Dealer.repository;

import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealerRepository extends MongoRepository<Dealer, String> {
    Optional<Dealer> findByMobile(String dealerMobile);
    Optional<Dealer> findById(String dealerMobile);
    Optional<Dealer> findByEmail(String dealerEmail);
    Optional<Dealer> findByRole(Role role);
    Optional<Dealer> findByOtp(String token);
}
