package com.xamplify.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	boolean existsByNameIgnoreCase(String name);
}
