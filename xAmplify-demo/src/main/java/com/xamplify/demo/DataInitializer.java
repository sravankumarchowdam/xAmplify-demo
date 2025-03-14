package com.xamplify.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.modal.Company;
import com.xamplify.demo.modal.Role;
import com.xamplify.demo.modal.User;
import com.xamplify.demo.modal.UserCompany;
import com.xamplify.demo.modal.UserRole;
import com.xamplify.demo.repository.CompanyRepository;
import com.xamplify.demo.repository.RoleRepository;
import com.xamplify.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run(String... args) throws Exception {

		System.out.println("******************In Command Line Runner*****************");

		try {
			// ✅ Create Company
			Company company = Company.builder().name("xAmplify").domainName("xamplify.com").build();
			company = companyRepository.save(company);

			// ✅ Create Role
			Role vendorRole = Role.builder().name("VENDOR").build();
			vendorRole = roleRepository.save(vendorRole);

			Role partnerRole = Role.builder().name("PARTNER").build();
			partnerRole = roleRepository.save(partnerRole);

			// ✅ Create User (with encrypted password)
			User user = User.builder().emailAddress("sravan@xamplify.com").firstName("Sravan").lastName("Kumar")
					.build();

			// ✅ Assiging Company to User (UserCompany)
			UserCompany userCompany = new UserCompany();
			userCompany.setCompany(company);
			userCompany.setUser(user);

			addRoles(vendorRole, partnerRole, userCompany);

			addCompanies(user, userCompany);

			user = userRepository.save(user);

		} catch (Exception e) {
			throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
		}

	}

	private void addCompanies(User user, UserCompany userCompany) {
		Set<UserCompany> userCompanies = new HashSet<>();
		userCompanies.add(userCompany);
		user.setUserCompanies(userCompanies);
	}

	private void addRoles(Role vendorRole, Role partnerRole, UserCompany userCompany) {
		UserRole vendorUserRole = new UserRole();
		vendorUserRole.setRole(vendorRole);
		vendorUserRole.setUserCompany(userCompany);

		UserRole partnerUserRole = new UserRole();
		partnerUserRole.setRole(partnerRole);
		partnerUserRole.setUserCompany(userCompany);

		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(partnerUserRole);
		userRoles.add(vendorUserRole);
		userCompany.setUserRoles(userRoles);
	}

}
