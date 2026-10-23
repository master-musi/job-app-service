package net.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class JobAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobAppServiceApplication.class, args);
	}

}
