package com.xamplify.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.modal.Company;
import com.xamplify.demo.modal.CompanyModule;
import com.xamplify.demo.modal.CompanyModulePrivilege;
import com.xamplify.demo.modal.Privilege;
import com.xamplify.demo.repository.CompanyModulePrivilegeRepository;
import com.xamplify.demo.repository.CompanyModuleRepository;
import com.xamplify.demo.repository.CompanyRepository;
import com.xamplify.demo.repository.ModuleRepository;
import com.xamplify.demo.repository.PrivilegeRepository;

@Service
public class CompanyService {

	@Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyModuleRepository companyModuleRepository;

    @Autowired
    private CompanyModulePrivilegeRepository companyModulePrivilegeRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
    
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Optional<Company> getCompanyById(Long id) {
		return companyRepository.findById(id);
	}

	@Transactional
	public String saveCompany(Company company) {
		if (companyRepository.existsByNameIgnoreCase(company.getName())) {
			return "Company name already exists.";
		}
		if (companyRepository.existsByDomainNameIgnoreCase(company.getDomainName())) {
			return "Domain name already exists.";
		}
		companyRepository.save(company);
		return "Company saved successfully.";
	}

	@Transactional
    public String saveOrUpdateCompany(Company company, List<Long> moduleIds, List<String> customNames) {
        Company existingCompany;

        if (company.getId() != null) { // ✅ If updating a company
            existingCompany = companyRepository.findById(company.getId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            existingCompany.setName(company.getName());
            existingCompany.setDomainName(company.getDomainName());

            // ✅ Remove old module assignments
            companyModuleRepository.deleteByCompanyId(existingCompany.getId());
        } else { // ✅ If adding a new company
            if (companyRepository.existsByNameIgnoreCase(company.getName())) {
                return "Company name already exists.";
            }
            if (companyRepository.existsByDomainNameIgnoreCase(company.getDomainName())) {
                return "Domain name already exists.";
            }
            existingCompany = new Company();
            existingCompany.setName(company.getName());
            existingCompany.setDomainName(company.getDomainName());
        }

        // ✅ Assign Modules & Privileges
        Set<CompanyModule> companyModules = new HashSet<>();
        for (int i = 0; i < moduleIds.size(); i++) {
            Long moduleId = moduleIds.get(i);
            String customName = customNames.get(i);

            com.xamplify.demo.modal.Module module = moduleRepository.findById(moduleId)
                    .orElseThrow(() -> new RuntimeException("Module not found"));

            CompanyModule companyModule = new CompanyModule();
            companyModule.setCompany(existingCompany);
            companyModule.setModule(module);
            companyModule.setCustomName(customName);
            companyModules.add(companyModule);

            // ✅ Assign Privileges Automatically
            List<Privilege> modulePrivileges = privilegeRepository.findByModuleId(moduleId);
            for (Privilege privilege : modulePrivileges) {
                CompanyModulePrivilege companyModulePrivilege = new CompanyModulePrivilege();
                companyModulePrivilege.setCompanyModule(companyModule);
                companyModulePrivilege.setPrivilege(privilege);
                companyModulePrivilegeRepository.save(companyModulePrivilege);
            }
        }

        existingCompany.setCompanyModules(companyModules);
        companyRepository.save(existingCompany);
        return "Company, assigned modules, and privileges saved successfully.";
    }


	@Transactional
	public void deleteCompany(Long id) {
		companyRepository.deleteById(id);
	}

}
