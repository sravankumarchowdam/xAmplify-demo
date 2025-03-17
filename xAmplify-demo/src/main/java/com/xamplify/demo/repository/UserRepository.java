package com.xamplify.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // ✅ Check if a user exists by email (Case-Insensitive)
    boolean existsByEmailAddressIgnoreCase(String emailAddress);

    // ✅ Find user by email (Case-Insensitive)
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
}
