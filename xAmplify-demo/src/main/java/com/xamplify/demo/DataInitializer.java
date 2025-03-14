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
			Role role = Role.builder().name("ROLE_VENDOR").build();
			role = roleRepository.save(role);

			// ✅ Create User (with encrypted password)
			User user = User.builder().emailAddress("sravan@xamplify.com").firstName("Sravan").lastName("Kumar")
					.build();

			UserCompany userCompany = new UserCompany();
			userCompany.setCompany(company);
			userCompany.setUser(user);
			Set<UserCompany> userCompanies = new HashSet<>();
			userCompanies.add(userCompany);
			user.setUserCompanies(userCompanies);

			user = userRepository.save(user);

		} catch (Exception e) {
			throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
		}

	}

}
