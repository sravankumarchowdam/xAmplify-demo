package com.xamplify.demo.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, BigInteger> {

}
