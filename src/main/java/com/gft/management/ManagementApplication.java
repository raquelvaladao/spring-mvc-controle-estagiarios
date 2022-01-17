package com.gft.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

}
