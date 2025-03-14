package com.xamplify.demo;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.modal.Company;
import com.xamplify.demo.modal.CompanyModule;
import com.xamplify.demo.modal.CompanyModulePrivilege;
import com.xamplify.demo.modal.ModulePrivilege;
import com.xamplify.demo.modal.Privilege;
import com.xamplify.demo.modal.Role;
import com.xamplify.demo.modal.User;
import com.xamplify.demo.modal.UserCompany;
import com.xamplify.demo.modal.UserCompanyPrivilege;

import lombok.RequiredArgsConstructor;

interface UserRepository extends JpaRepository<User, UUID> {
}

interface CompanyRepository extends JpaRepository<Company, Long> {
}

interface RoleRepository extends JpaRepository<Role, BigInteger> {
}

interface ModuleRepository extends JpaRepository<com.xamplify.demo.modal.Module, BigInteger> {
}

interface PrivilegeRepository extends JpaRepository<Privilege, BigInteger> {
}

interface UserCompanyRepository extends JpaRepository<UserCompany, BigInteger> {
}

interface CompanyModuleRepository extends JpaRepository<CompanyModule, BigInteger> {
}

interface ModulePrivilegeRepository extends JpaRepository<ModulePrivilege, BigInteger> {
}

interface CompanyModulePrivilegeRepository extends JpaRepository<CompanyModulePrivilege, BigInteger> {
}

interface UserCompanyPrivilegeRepository extends JpaRepository<UserCompanyPrivilege, BigInteger> {
}

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final CompanyRepository companyRepository;
	private final RoleRepository roleRepository;
	private final ModuleRepository moduleRepository;
	private final PrivilegeRepository privilegeRepository;
	private final UserCompanyRepository userCompanyRepository;
	private final CompanyModuleRepository companyModuleRepository;
	private final ModulePrivilegeRepository modulePrivilegeRepository;
	private final CompanyModulePrivilegeRepository companyModulePrivilegeRepository;
	private final UserCompanyPrivilegeRepository userCompanyPrivilegeRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run(String... args) throws Exception {

		try {
			// ✅ Create Company
			Company company = Company.builder().name("xAmplify").domainName("xamplify.com").build();
			company = companyRepository.save(company);

			// ✅ Create Role
			Role role = Role.builder().name("ROLE_VENDOR").build();
			role = roleRepository.save(role);

			// ✅ Create User (with encrypted password)
			User user = User.builder().emailAddress("sravan@xamplify.com").build();
			user = userRepository.save(user);

		

		} catch (Exception e) {
			throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
		}

	}

}
