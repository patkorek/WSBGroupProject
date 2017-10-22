package com.WSBGroupProject.controller;

import java.net.URI;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.WSBGroupProject.repository.AccountRepository;
import com.WSBGroupProject.model.Account;
import com.WSBGroupProject.error.UserAlreadyExistsException;
import com.WSBGroupProject.error.UserNotFoundException;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
	@Autowired
	AccountRepository accountRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Account> readAccounts() {
		return accountRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Account input) {
		if (accountRepository.findByUsername(input.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException(input.getUsername());
		}

		Account result = accountRepository.save(new Account(input));

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{username}")
			.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public Account readAccount(@PathVariable String username) {
		return accountRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException(username));
	}
}
