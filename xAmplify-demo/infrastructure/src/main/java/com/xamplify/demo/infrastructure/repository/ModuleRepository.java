package com.xamplify.demo.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.domain.model.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

	boolean existsByNameIgnoreCase(String name);

}
