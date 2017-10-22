package com.WSBGroupProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.WSBGroupProject.repository.AccountRepository;
import com.WSBGroupProject.model.Account;
import java.util.Arrays;

@SpringBootApplication
public class WsbGroupProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsbGroupProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository) {
		return (evt) -> Arrays.asList("admin,user,test".split(","))
				.forEach(
						a -> {
							Account account = accountRepository.save(new Account(a, a));
						});
	}
}
