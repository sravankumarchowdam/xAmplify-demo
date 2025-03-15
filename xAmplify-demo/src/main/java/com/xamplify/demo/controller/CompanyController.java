package com.xamplify.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xamplify.demo.modal.Company;
import com.xamplify.demo.repository.ModuleRepository;
import com.xamplify.demo.service.CompanyService;

@Controller
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ModuleRepository moduleRepository;

	@GetMapping
	public String listCompanies(Model model) {
		model.addAttribute("companies", companyService.getAllCompanies());
		model.addAttribute("modules", moduleRepository.findAll());
		model.addAttribute("company", new Company());
		return "company";
	}

	@PostMapping("/saveOrUpdate")
	public String saveOrUpdateCompany(@ModelAttribute Company company,
			@RequestParam(required = false) List<Long> moduleIds,
			@RequestParam(required = false) List<String> customNames, RedirectAttributes redirectAttributes) {
		try {
			String message = companyService.saveOrUpdateCompany(company, moduleIds, customNames);
			redirectAttributes.addFlashAttribute("successMessage", message);
		} catch (RuntimeException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
		}
		return "redirect:/companies";
	}

	@GetMapping("/edit/{id}")
	public String editCompany(@PathVariable Long id, Model model) {
		model.addAttribute("company", companyService.getCompanyById(id));
		model.addAttribute("modules", moduleRepository.findAll());
		model.addAttribute("companies", companyService.getAllCompanies());
		return "company"; // ✅ Returns to the same form for editing
	}

	@GetMapping("/delete/{id}")
	public String deleteCompany(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		companyService.deleteCompany(id);
		redirectAttributes.addFlashAttribute("successMessage", "Company deleted successfully!");
		return "redirect:/companies";
	}
}
