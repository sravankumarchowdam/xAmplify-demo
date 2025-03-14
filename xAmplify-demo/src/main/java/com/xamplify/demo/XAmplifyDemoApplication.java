package com.xamplify.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.xamplify.demo.modal") // ✅ Ensure entity scanning
@EnableJpaRepositories(basePackages = "com.xamplify.demo.repository")
public class XAmplifyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XAmplifyDemoApplication.class, args);
	}

}
