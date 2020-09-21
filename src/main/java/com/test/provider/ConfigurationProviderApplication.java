package com.test.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class ConfigurationProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationProviderApplication.class, args);
	}

}
