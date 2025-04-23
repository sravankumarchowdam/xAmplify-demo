package com.xamplify.demo.presentation.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
public class XamplifyDemoController {

	// 🔹 Loads the home.html page when the application starts
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message", "Welcome to Thymeleaf Page!");
		return "home"; // Loads src/main/resources/templates/home.html
	}

	// 🔹 Thymeleaf: Returns an HTML page
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("username", "John Doe");
		return "dashboard"; // Loads src/main/resources/templates/dashboard.html
	}

	// 🔹 JSON Response: Returns data instead of a page
	@GetMapping("/api/data")
	@ResponseBody // Ensures JSON response
	public Map<String, String> getData() {
		return Map.of("status", "success", "message", "This is a JSON response");
	}

}
