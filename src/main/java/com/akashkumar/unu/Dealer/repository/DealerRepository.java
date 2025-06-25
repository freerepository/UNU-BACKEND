package com.akashkumar.unu.Dealer.repository;

import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealerRepository extends MongoRepository<Dealer, String> {
    Optional<Dealer> findByDealerMobile(String dealerMobile);
    Optional<Dealer> findByDealerId(String dealerMobile);
    Optional<Dealer> findByDealerEmail(String dealerEmail);
    Optional<Dealer> findByRole(Role role);
    Optional<Dealer> findByOtp(String token);
}
