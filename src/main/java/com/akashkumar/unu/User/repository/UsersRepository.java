package com.akashkumar.unu.User.repository;

import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    Optional<Users> findByUserMobile(String UserMobile);
    Optional<Users> findByUserEmail(String userEmail);
    Optional<Users> findByOtp(String token);
    Optional<Users> findByRole(Role role);
}
