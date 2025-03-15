package com.xamplify.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xamplify.demo.exception.DuplicateRoleException;
import com.xamplify.demo.modal.Role;
import com.xamplify.demo.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping
	public String listRoles(Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		model.addAttribute("role", new Role());
		return "role";
	}

	// ✅ Save role to database
	@PostMapping("/save")
	public String saveRole(@ModelAttribute Role role, Model model) {
		try {
			roleService.saveRole(role);
			model.addAttribute("successMessage", "Role saved successfully!");
		} catch (DuplicateRoleException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
		} catch (Exception ex) {
			model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
		}

		model.addAttribute("role", new Role()); // Ensure form is not pre-filled with old data
		return "role"; // Stay on the same page instead of redirecting
	}

	// ✅ Edit role (Load form with existing data)
	@GetMapping("/edit/{id}")
	public String editRole(@PathVariable Long id, Model model) {
		Role role = roleService.getRoleById(id);
		if (role != null) {
			model.addAttribute("role", role);
			return "role"; // Reuse role.html for editing
		}
		return "redirect:/roles/list";
	}

	// ✅ Delete role
	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return "redirect:/roles/list";
	}
}
