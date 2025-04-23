package com.xamplify.demo.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.domain.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	boolean existsByNameIgnoreCase(String name);
}
