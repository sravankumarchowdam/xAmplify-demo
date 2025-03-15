package com.xamplify.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xamplify.demo.modal.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	boolean existsByNameIgnoreCase(String name);

	@Query("SELECT COUNT(p) > 0 FROM Privilege p WHERE LOWER(p.name) = LOWER(:name) AND p.id <> :id")
	boolean existsByNameIgnoreCaseAndNotId(@Param("name") String name, @Param("id") Long id);

}
