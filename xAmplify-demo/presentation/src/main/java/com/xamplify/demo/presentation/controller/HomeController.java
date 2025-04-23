package com.xamplify.demo.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String redirectToHome() {
		return "home"; // Redirect to Thymeleaf home page
	}

}
