package com.xamplify.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.CompanyModulePrivilege;

@Repository
public interface CompanyModulePrivilegeRepository extends JpaRepository<CompanyModulePrivilege, Long> {
    List<CompanyModulePrivilege> findByCompanyModuleId(Long companyModuleId);
    void deleteByCompanyModuleId(Long companyModuleId);
}
