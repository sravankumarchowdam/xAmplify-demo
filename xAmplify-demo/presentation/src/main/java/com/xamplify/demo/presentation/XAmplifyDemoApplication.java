package com.xamplify.demo.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.xamplify.demo")
@EntityScan("com.xamplify.demo.domain.model")
@EnableJpaRepositories("com.xamplify.demo.infrastructure.repository")
public class XAmplifyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(XAmplifyDemoApplication.class, args);
	}

}
