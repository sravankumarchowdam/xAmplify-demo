package com.xamplify.demo.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xamplify.demo.domain.model.Privilege;
import com.xamplify.demo.application.service.PrivilegeService;

@Controller
@RequestMapping("/privileges")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping
    public String showPrivileges(Model model) {
        model.addAttribute("privileges", privilegeService.getAllPrivileges());
        model.addAttribute("privilege", new Privilege()); // Empty form for adding
        return "privilege"; // Thymeleaf template name
    }

    @PostMapping("/save")
    public String savePrivilege(@ModelAttribute Privilege privilege, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.savePrivilege(privilege);
            redirectAttributes.addFlashAttribute("successMessage", "Privilege added successfully!");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/privileges";
    }

    @GetMapping("/edit/{id}")
    public String editPrivilege(@PathVariable Long id, Model model) {
        model.addAttribute("privilege", privilegeService.getPrivilegeById(id));
        model.addAttribute("privileges", privilegeService.getAllPrivileges());
        return "privilege";
    }

    @GetMapping("/delete/{id}")
    public String deletePrivilege(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            privilegeService.deletePrivilege(id);
            redirectAttributes.addFlashAttribute("successMessage", "Privilege deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete privilege.");
        }
        return "redirect:/privileges";
    }
}

