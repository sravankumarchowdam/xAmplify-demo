package com.xamplify.demo.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.domain.model.Company;
import com.xamplify.demo.domain.model.CompanyModulePrivilege;
import com.xamplify.demo.domain.model.User;
import com.xamplify.demo.domain.model.UserCompany;
import com.xamplify.demo.domain.model.UserCompanyPrivilege;
import com.xamplify.demo.infrastructure.repository.CompanyModulePrivilegeRepository;
import com.xamplify.demo.infrastructure.repository.UserCompanyPrivilegeRepository;
import com.xamplify.demo.infrastructure.repository.UserCompanyRepository;
import com.xamplify.demo.infrastructure.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCompanyRepository userCompanyRepository;

	@Autowired
	private UserCompanyPrivilegeRepository userCompanyPrivilegeRepository;

	@Autowired
	private CompanyModulePrivilegeRepository companyModulePrivilegeRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public String saveUser(User user, Long companyId, List<Long> moduleIds) {
		return "User saved successfully.";

	}

	@Transactional
	public String saveOrUpdateUser(User user, Long companyId, List<Long> moduleIds) {
		User existingUser;

		if (user.getId() != null) { // ✅ If updating
			existingUser = userRepository.findById(user.getId())
					.orElseThrow(() -> new RuntimeException("User not found"));

			existingUser.setEmailAddress(user.getEmailAddress());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());

			// ✅ Find UserCompany before deleting privileges
			List<UserCompany> userCompanies = userCompanyRepository.findByUserId(existingUser.getId());
			for (UserCompany userCompany : userCompanies) {
				userCompanyPrivilegeRepository.deleteByUserCompanyId(userCompany.getId()); // ✅ Now passing correct
																							// userCompanyId
			}

			// ✅ Remove old UserCompany mappings
			userCompanyRepository.deleteByUserId(existingUser.getId());
		} else { // ✅ If adding a new user
			if (userRepository.existsByEmailAddressIgnoreCase(user.getEmailAddress())) {
				return "Email address already exists.";
			}
			existingUser = new User();
			existingUser.setEmailAddress(user.getEmailAddress());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
		}

		// ✅ Assign User to Company
		UserCompany userCompany = new UserCompany();
		userCompany.setUser(existingUser);
		Company company = new Company();
		company.setId(companyId);
		userCompany.setCompany(company);
		userCompany.setUser(existingUser);

		// ✅ Assign Modules & Privileges
		if (moduleIds != null) {
			for (Long moduleId : moduleIds) {
				List<CompanyModulePrivilege> modulePrivileges = companyModulePrivilegeRepository
						.findByCompanyModule_ModuleId(moduleId);

				for (CompanyModulePrivilege privilege : modulePrivileges) {
					UserCompanyPrivilege userCompanyPrivilege = new UserCompanyPrivilege();
					userCompanyPrivilege.setUserCompany(userCompany);
					userCompanyPrivilege.setCompanyModulePrivilege(privilege);
					userCompanyPrivilegeRepository.save(userCompanyPrivilege);
				}
			}
		}
		Set<UserCompany> userCompanies = new HashSet<>();
		userCompanies.add(userCompany);
		existingUser.setUserCompanies(userCompanies);
		userRepository.save(existingUser);
		return "User saved successfully.";
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(UUID userId) { // ✅ User ID remains UUID
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Transactional
	public void deleteUser(UUID userId) { // ✅ User ID remains UUID
		List<UserCompany> userCompanies = userCompanyRepository.findByUserId(userId);

		for (UserCompany userCompany : userCompanies) {
			userCompanyPrivilegeRepository.deleteByUserCompanyId(userCompany.getId()); // ✅ Corrected to use
																						// `userCompanyId`
			userCompanyRepository.deleteById(userCompany.getId());
		}

		userRepository.deleteById(userId);
	}
}
