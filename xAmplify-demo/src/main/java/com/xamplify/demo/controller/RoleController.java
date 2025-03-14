package com.xamplify.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xamplify.demo.modal.Role;
import com.xamplify.demo.service.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	// ✅ Show the role form
	@GetMapping("/new")
	public String showRoleForm(Model model) {
		model.addAttribute("role", new Role());
		return "role"; // Loads role.html
	}

	// ✅ Save role to database
	@PostMapping("/save")
	public String saveRole(@ModelAttribute Role role) {
		roleService.saveRole(role);
		return "redirect:/roles/list";
	}

	// ✅ Display all roles
	@GetMapping("/list")
	public String listRoles(Model model) {
		model.addAttribute("roles", roleService.getAllRoles());
		return "role-list"; // Loads role-list.html
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
