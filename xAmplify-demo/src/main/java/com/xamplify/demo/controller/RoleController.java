package com.xamplify.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String saveOrUpdateRole(@ModelAttribute Role role, Model model) {
		try {
			// Check if we are updating an existing role
			if (role.getId() != null) {
				roleService.updateRole(role);
				model.addAttribute("successMessage", "Role updated successfully!");
			} else {
				roleService.saveRole(role);
				model.addAttribute("successMessage", "Role saved successfully!");
			}
		} catch (DuplicateRoleException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
		} catch (Exception ex) {
			model.addAttribute("errorMessage", "An unexpected error occurred. Please try again.");
		}

		// Reload roles list
		model.addAttribute("roles", roleService.getAllRoles());

		// Reset the form
		model.addAttribute("role", new Role());

		return "role"; // Stay on the same page
	}

	// ✅ Edit role (Load form with existing data)
	@GetMapping("/edit/{id}")
	public String editRole(@PathVariable Long id, Model model) {
		Role role = roleService.getRoleById(id);
		model.addAttribute("roles", roleService.getAllRoles()); // Populate the table
		model.addAttribute("role", role); // Populate the form with existing data
		return "role"; // Same template
	}

	// ✅ Delete role
	@GetMapping("/delete/{id}")
	public String deleteRole(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			roleService.deleteRole(id);
			redirectAttributes.addFlashAttribute("successMessage", "Role deleted successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete role. It may be in use.");
		}

		return "redirect:/roles"; // Redirect to the roles page
	}
}
