package com.xamplify.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.UserCompany;

@Repository
public interface UserCompanyRepository extends JpaRepository<UserCompany, Long> {
	List<UserCompany> findByUserId(UUID userId);

	void deleteByUserId(UUID userId);
}
