package br.com.usermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UsermanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagerApplication.class, args);
	}

}
