package com.xamplify.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
