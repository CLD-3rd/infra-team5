package com.team5.surbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SurbeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurbeeApplication.class, args);
	}

}
