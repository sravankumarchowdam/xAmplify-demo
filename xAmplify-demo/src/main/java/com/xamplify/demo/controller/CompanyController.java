package com.xamplify.demo.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xamplify.demo.modal.Company;
import com.xamplify.demo.service.CompanyService;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public String listCompanies(Model model) {
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("company", new Company()); // Form object
        return "company";
    }

    @PostMapping("/save")
    public String saveCompany(@ModelAttribute Company company, Model model) {
        String message = companyService.saveCompany(company);
        if (message.contains("already exists")) {
            model.addAttribute("errorMessage", message);
        } else {
            model.addAttribute("successMessage", message);
        }
        model.addAttribute("companies", companyService.getAllCompanies());
        model.addAttribute("company", new Company());
        return "company"; // Stay on the same page
    }

    @GetMapping("/edit/{id}")
    public String editCompany(@PathVariable Long id, Model model) {
        Optional<Company> company = companyService.getCompanyById(id);
        model.addAttribute("company", company.orElse(new Company()));
        model.addAttribute("companies", companyService.getAllCompanies());
        return "company";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id, Model model) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }
}

