package com.xamplify.demo.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.domain.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	boolean existsByNameIgnoreCase(String name);
    boolean existsByDomainNameIgnoreCase(String domainName);

}
