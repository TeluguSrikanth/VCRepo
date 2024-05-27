package com.voter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class VcListApplication {

	public static void main(String[] args) {
		SpringApplication.run(VcListApplication.class, args);
	}

}
