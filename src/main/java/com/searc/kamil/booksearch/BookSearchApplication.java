package com.searc.kamil.booksearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
public class BookSearchApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}


	
	public static void main(String[] args) {
		SpringApplication.run(BookSearchApplication.class, args);
	}

}
