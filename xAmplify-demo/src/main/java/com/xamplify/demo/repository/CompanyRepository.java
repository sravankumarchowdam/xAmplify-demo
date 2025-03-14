package com.xamplify.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
