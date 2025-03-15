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

import com.xamplify.demo.modal.Module;
import com.xamplify.demo.service.ModuleService;

@Controller
@RequestMapping("/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping
    public String showModules(Model model) {
        model.addAttribute("modules", moduleService.getAllModules());
        model.addAttribute("module", new Module());
        return "module";
    }

    @PostMapping("/save")
    public String saveModule(@ModelAttribute Module module,
                             @RequestParam List<String> privilegeNames,
                             RedirectAttributes redirectAttributes) {
        String message = moduleService.saveModule(module, privilegeNames);
        if (message.contains("already exists")) {
            redirectAttributes.addFlashAttribute("errorMessage", message);
        } else {
            redirectAttributes.addFlashAttribute("successMessage", message);
        }
        return "redirect:/modules";
    }

    @GetMapping("/edit/{id}")
    public String editModule(@PathVariable Long id, Model model) {
        model.addAttribute("module", moduleService.getAllModules());
        return "module";
    }

    @GetMapping("/delete/{id}")
    public String deleteModule(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        moduleService.deleteModule(id);
        redirectAttributes.addFlashAttribute("successMessage", "Module deleted successfully!");
        return "redirect:/modules";
    }
}
