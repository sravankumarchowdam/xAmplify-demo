package com.xamplify.demo.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.domain.model.UserCompanyPrivilege;

@Repository
public interface UserCompanyPrivilegeRepository extends JpaRepository<UserCompanyPrivilege, Long> {

    // ✅ Fetch privileges assigned to a user in a company
    List<UserCompanyPrivilege> findByUserCompanyId(Long userCompanyId);

    // ✅ Delete privileges when a user is removed from a company
    void deleteByUserCompanyId(Long userCompanyId);
}
