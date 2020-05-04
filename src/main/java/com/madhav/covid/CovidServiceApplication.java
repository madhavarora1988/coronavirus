package com.madhav.covid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.madhav.covid.repositories")
@EntityScan(basePackages = "com.madhav.covid.models")
@ComponentScan(basePackages = "com.madhav")
public class CovidServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidServiceApplication.class, args);
	}

}
