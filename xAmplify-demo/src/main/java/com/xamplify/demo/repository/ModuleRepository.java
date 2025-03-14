package com.xamplify.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

}
