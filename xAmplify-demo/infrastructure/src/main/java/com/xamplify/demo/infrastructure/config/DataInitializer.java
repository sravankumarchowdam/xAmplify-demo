package com.xamplify.demo.infrastructure.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.domain.model.Company;
import com.xamplify.demo.domain.model.CompanyModule;
import com.xamplify.demo.domain.model.Privilege;
import com.xamplify.demo.domain.model.Role;
import com.xamplify.demo.domain.model.User;
import com.xamplify.demo.domain.model.UserCompany;
import com.xamplify.demo.domain.model.UserRole;
import com.xamplify.demo.infrastructure.repository.CompanyRepository;
import com.xamplify.demo.infrastructure.repository.ModuleRepository;
import com.xamplify.demo.infrastructure.repository.PrivilegeRepository;
import com.xamplify.demo.infrastructure.repository.RoleRepository;
import com.xamplify.demo.infrastructure.repository.UserRepository;

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

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run(String... args) throws Exception {

		// extracted();

	}

	/**
	 * 
	 */
	private void extracted() {
		System.out.println("******************In Command Line Runner*****************");

		try {
			// ✅ Create Company
			Company company = Company.builder().name("xAmplify").domainName("xamplify.com").build();

			Set<CompanyModule> companyModules = new HashSet<>();

			/*** Email Template Module Mapping ****/
			com.xamplify.demo.modal.Module emailTemplateModule = new com.xamplify.demo.modal.Module();
			emailTemplateModule.setName("Email Templates");
			moduleRepository.save(emailTemplateModule);
			CompanyModule emailTemplateCompanyModule = new CompanyModule();
			emailTemplateCompanyModule.setCompany(company);
			emailTemplateCompanyModule.setModule(emailTemplateModule);
			emailTemplateCompanyModule.setCustomName(emailTemplateModule.getName());
			companyModules.add(emailTemplateCompanyModule);

			/*** Assets Module Mapping ****/
			com.xamplify.demo.modal.Module assetsModule = new com.xamplify.demo.modal.Module();
			assetsModule.setName("Assets");
			moduleRepository.save(assetsModule);
			CompanyModule assetsCompanyModule = new CompanyModule();
			assetsCompanyModule.setCompany(company);
			assetsCompanyModule.setModule(assetsModule);
			assetsCompanyModule.setCustomName(assetsModule.getName());
			companyModules.add(assetsCompanyModule);

			/*** Tracks Module Mapping ****/
			com.xamplify.demo.modal.Module tracksModule = new com.xamplify.demo.modal.Module();
			tracksModule.setName("Tracks");
			moduleRepository.save(tracksModule);
			CompanyModule tracksCompanyModule = new CompanyModule();
			tracksCompanyModule.setCompany(company);
			tracksCompanyModule.setModule(tracksModule);
			tracksCompanyModule.setCustomName(tracksModule.getName());
			companyModules.add(tracksCompanyModule);

			/*** PlayBooks Module Mapping ****/
			com.xamplify.demo.modal.Module playBooksModule = new com.xamplify.demo.modal.Module();
			playBooksModule.setName("PlayBooks");
			moduleRepository.save(playBooksModule);
			CompanyModule playBooksCompanyModule = new CompanyModule();
			playBooksCompanyModule.setCompany(company);
			playBooksCompanyModule.setModule(playBooksModule);
			playBooksCompanyModule.setCustomName(playBooksModule.getName());
			companyModules.add(playBooksCompanyModule);

			company.setCompanyModules(companyModules);
			company = companyRepository.save(company);

			// ✅ Create Role
			Set<Role> roles = addRoles();

			// ✅ Create User (with encrypted password)
			User user = User.builder().emailAddress("sravan@xamplify.com").firstName("Sravan").lastName("Kumar")
					.build();

			// ✅ Assiging Company to User (UserCompany)
			UserCompany userCompany = new UserCompany();
			userCompany.setCompany(company);
			userCompany.setUser(user);

			addRoles(roles, userCompany);

			mapUserAndCompany(user, userCompany);

			user = userRepository.save(user);

			Privilege addEmailTemplatePrivilege = new Privilege();
			addEmailTemplatePrivilege.setName("ADD_EMAIL_TEMPLATE");
			privilegeRepository.save(addEmailTemplatePrivilege);

			Privilege editEmailTemplatePrivilege = new Privilege();
			editEmailTemplatePrivilege.setName("EDIT_EMAIL_TEMPLATE");
			privilegeRepository.save(editEmailTemplatePrivilege);

			Privilege viewEmailTemplatePrivilege = new Privilege();
			viewEmailTemplatePrivilege.setName("VIEW_EMAIL_TEMPLATE");
			privilegeRepository.save(viewEmailTemplatePrivilege);

			Privilege deleteEmailTemplatePrivilege = new Privilege();
			deleteEmailTemplatePrivilege.setName("DELETE_EMAIL_TEMPLATE");
			privilegeRepository.save(deleteEmailTemplatePrivilege);

			Privilege addAssetPrivilege = new Privilege();
			addAssetPrivilege.setName("ADD_ASSET");
			privilegeRepository.save(addAssetPrivilege);

			Privilege editAssetPrivilege = new Privilege();
			editAssetPrivilege.setName("EDIT_ASSET");
			privilegeRepository.save(editAssetPrivilege);

			Privilege viewAssetPrivilege = new Privilege();
			viewAssetPrivilege.setName("VIEW_ASSET");
			privilegeRepository.save(viewAssetPrivilege);

			Privilege deleteAssetPrivilege = new Privilege();
			deleteAssetPrivilege.setName("DELETE_ASSET");
			privilegeRepository.save(deleteAssetPrivilege);

			Privilege addTrackPrivilege = new Privilege();
			addTrackPrivilege.setName("ADD_TRACK");
			privilegeRepository.save(addTrackPrivilege);

			Privilege editTrackPrivilege = new Privilege();
			editTrackPrivilege.setName("EDIT_TRACK");
			privilegeRepository.save(editTrackPrivilege);

			Privilege viewTrackPrivilege = new Privilege();
			viewTrackPrivilege.setName("VIEW_TRACK");
			privilegeRepository.save(viewTrackPrivilege);

			Privilege deleteTrackPrivilege = new Privilege();
			deleteTrackPrivilege.setName("DELETE_TRACK");
			privilegeRepository.save(deleteTrackPrivilege);

			Privilege addPlayBookPrivilege = new Privilege();
			addPlayBookPrivilege.setName("ADD_PLAY_BOOK");
			privilegeRepository.save(addPlayBookPrivilege);

			Privilege editPlayBookPrivilege = new Privilege();
			editPlayBookPrivilege.setName("EDIT_PLAY_BOOK");
			privilegeRepository.save(editPlayBookPrivilege);

			Privilege viewPlayBookPrivilege = new Privilege();
			viewPlayBookPrivilege.setName("VIEW_PLAY_BOOK");
			privilegeRepository.save(viewPlayBookPrivilege);

			Privilege deletePlayBookPrivilege = new Privilege();
			deletePlayBookPrivilege.setName("DELETE_PLAY_BOOK");
			privilegeRepository.save(deletePlayBookPrivilege);

		} catch (Exception e) {
			throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
		}
	}

	/**
	 * @return
	 */
	private Set<Role> addRoles() {
		Role vendorRole = Role.builder().name("VENDOR").build();
		vendorRole = roleRepository.save(vendorRole);

		Role partnerRole = Role.builder().name("PARTNER").build();
		partnerRole = roleRepository.save(partnerRole);

		Set<Role> roles = new HashSet<>();
		roles.add(vendorRole);
		roles.add(partnerRole);
		return roles;
	}

	private void mapUserAndCompany(User user, UserCompany userCompany) {
		Set<UserCompany> userCompanies = new HashSet<>();
		userCompanies.add(userCompany);
		user.setUserCompanies(userCompanies);
	}

	private void addRoles(Set<Role> roles, UserCompany userCompany) {
		Set<UserRole> userRoles = new HashSet<>();
		for (Role role : roles) {
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUserCompany(userCompany);
			userRoles.add(userRole);
		}
		userCompany.setUserRoles(userRoles);
	}

}
