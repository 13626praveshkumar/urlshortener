package com.systemdesign.urlshortenerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class UrlshortenerappApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlshortenerappApplication.class, args);
	}

}
