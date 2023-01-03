package com.anthony.elasticsave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ElasticsaveApplication {

	// Check whether 'my-bucket-name' exists or not.
	public static void main(String[] args) {
		SpringApplication.run(ElasticsaveApplication.class, args);
	}

}
