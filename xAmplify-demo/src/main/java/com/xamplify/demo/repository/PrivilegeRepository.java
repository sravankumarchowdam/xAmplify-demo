package com.xamplify.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
