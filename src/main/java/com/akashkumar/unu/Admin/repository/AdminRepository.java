package com.akashkumar.unu.Admin.repository;

import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByRole(Role role);
    Optional<Admin> findByAdminMobile(String adminMobile);
    Optional<Admin> findByAdminEmail(String adminEmail);
    Optional<Admin> findByOtp(String token);
}
