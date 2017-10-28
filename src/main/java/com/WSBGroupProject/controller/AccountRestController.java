package com.WSBGroupProject.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.WSBGroupProject.repository.AccountRepository;
import com.WSBGroupProject.model.Account;
import com.WSBGroupProject.error.UserAlreadyExistsException;
import com.WSBGroupProject.error.UserNotFoundException;
import com.WSBGroupProject.constants.Constants;
import com.WSBGroupProject.services.EmailService;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	EmailService emailService;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Account> readAccounts() {
		return accountRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Account input) {
		if (accountRepository.findByUsername(input.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException(input.getUsername());
		}
		
		Map<String, String> errors = verifyInput(input); 
		
		if (!errors.isEmpty()) {
			return new ResponseEntity<Map<String, String>>(errors, HttpStatus.OK);
		}

		Account result = accountRepository.save(new Account(input));
		emailService.signUp(input.getUsername());

		return new ResponseEntity<Account>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username:.+}")
	public Account readAccount(@PathVariable String username) {
		return accountRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException(username));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<?> login(@RequestBody Account input) {
		Account account = accountRepository.findByUsername(input.getUsername())
				.orElseThrow(() -> new UserNotFoundException(input.getUsername()));
		
		if (account.getPassword().equals(input.getPassword())) {
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}
		else {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("message", "Bad credentials");
			return new ResponseEntity<Map<String, String>>(errorResponse, HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/reset/{username:.+}")
	public Boolean resetPassword(@PathVariable String username) {
		Account account = accountRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException(username));
		account.setPassword(emailService.resetPassword(username));
		accountRepository.save(account);
		return Boolean.TRUE;
	}

	private Map<String, String> verifyInput(Account input) {
		Map<String, String> map = new HashMap<>();
		if ( !Pattern.compile(Constants.EMAIL_PATTERN)
				.matcher(input.getUsername())
				.matches() ) {
			map.put("username","Email is not proper");
		}
		if ( !Pattern.compile(Constants.PASSWORD_PATTERN)
				.matcher(input.getPassword())
				.matches() ) {
			map.put("password","Password is not proper");
		}
		if ( !Pattern.compile(Constants.FIRSTNAME_PATTERN)
				.matcher(input.getFirstName())
				.matches() ) {
			map.put("firstName","First name is not proper");
		}
		if ( !Pattern.compile(Constants.LASTNAME_PATTERN)
				.matcher(input.getLastName())
				.matches() ) {
			map.put("lastName","Last name is not proper");
		}
		if ( !Pattern.compile(Constants.PHONE_PATTERN)
				.matcher(input.getPhone())
				.matches() ) {
			map.put("phone","Phone is not proper");
		}
		if ( !Pattern.compile(Constants.DATEOFBIRTH_PATTERN)
				.matcher(input.getDateOfBirth())
				.matches() ) {
			map.put("dateOfBirth","Date of birth is not proper");
		}
		if ( !Pattern.compile(Constants.HOUSENUMBER_PATTERN)
				.matcher(input.getHouseNumber())
				.matches() ) {
			map.put("houseNumber","House number is not proper");
		}
		if ( !Pattern.compile(Constants.CITY_PATTERN)
				.matcher(input.getCity())
				.matches() ) {
			map.put("city","City is not proper");
		}
		if ( !Pattern.compile(Constants.POSTCODE_PATTERN)
				.matcher(input.getPostCode())
				.matches() ) {
			map.put("postCode","Post code is not proper");
		}
		if ( !Pattern.compile(Constants.STREET_PATTERN)
				.matcher(input.getStreet())
				.matches() ) {
			map.put("street","Street is not proper");
		}
		if ( !Pattern.compile(Constants.COMPANYNAME_PATTERN)
				.matcher(input.getCompanyName())
				.matches() ) {
			map.put("companyName","Company name is not proper");
		}
		if ( !Pattern.compile(Constants.COMPANYADDRESS_PATTERN)
				.matcher(input.getCompanyAddress())
				.matches() ) {
			map.put("companyAddress","Company address is not proper");
		}
		if ( !Pattern.compile(Constants.NIP_PATTERN)
				.matcher(input.getNip())
				.matches() ) {
			map.put("nip","NIP is not proper");
		}
		
		return map;
	}
}
