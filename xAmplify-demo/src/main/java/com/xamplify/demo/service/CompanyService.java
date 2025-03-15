package com.xamplify.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xamplify.demo.modal.Company;
import com.xamplify.demo.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
    private CompanyRepository companyRepository;

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
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

}
