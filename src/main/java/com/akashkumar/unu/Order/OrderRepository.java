package com.akashkumar.unu.Order;

import com.akashkumar.unu.User.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Orders, String> {
    Optional<Users> findByUserId(String userId);
    Optional<Orders> findByOrderId(String orderId);
}
