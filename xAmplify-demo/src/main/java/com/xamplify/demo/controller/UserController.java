package com.xamplify.demo.controller;

import java.util.List;
import java.util.UUID;

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

import com.xamplify.demo.modal.User;
import com.xamplify.demo.repository.CompanyModuleRepository;
import com.xamplify.demo.repository.CompanyRepository;
import com.xamplify.demo.repository.RoleRepository;
import com.xamplify.demo.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyModuleRepository companyModuleRepository;

    @GetMapping
    public String showUsers(Model model) {
    	model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("modules", companyModuleRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll()); // ✅ Add roles to model
        model.addAttribute("user", new User());
        return "user"; // ✅ Returns user.html
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateUser(@ModelAttribute User user,
                                   @RequestParam Long companyId, // ✅ Corrected to Long
                                   @RequestParam(required = false) List<Long> moduleIds,
                                   RedirectAttributes redirectAttributes) {
        try {
            String message = userService.saveOrUpdateUser(user, companyId, moduleIds);
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving user: " + ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable UUID id, Model model) { // ✅ User ID remains UUID
    	  model.addAttribute("user", userService.getUserById(id));
          model.addAttribute("companies", companyRepository.findAll());
          model.addAttribute("modules", companyModuleRepository.findAll());
          model.addAttribute("roles", roleRepository.findAll()); // ✅ Add roles to model
          return "user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id, RedirectAttributes redirectAttributes) { // ✅ User ID remains UUID
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable UUID id, Model model) { // ✅ User ID remains UUID
        model.addAttribute("user", userService.getUserById(id));
        return "user_details"; // ✅ Separate view page (if needed)
    }
}

