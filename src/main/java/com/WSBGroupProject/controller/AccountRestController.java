package com.WSBGroupProject.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.WSBGroupProject.repository.AccountRepository;
import com.WSBGroupProject.model.Account;
import com.WSBGroupProject.dto.*;
import com.WSBGroupProject.error.UserAlreadyExistsException;
import com.WSBGroupProject.error.UserNotFoundException;
import com.WSBGroupProject.constants.Constants;
import com.WSBGroupProject.services.EmailService;
import com.WSBGroupProject.services.StringService;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	StringService stringService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseDTO getAccounts() {
		ResponseDTO response = new ResponseDTO("success",
				new ArrayList<CodeDTO>(),
				"",
				accountRepository.findAll());
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseDTO postAccount(@RequestBody UserForm input) {
		ResponseDTO response = new ResponseDTO(input);
		boolean signup = (input.getId() == null);
		Account newAccount = new Account(input);
		Optional<Account> oldAccount = signup ? 
				accountRepository.findByUsername(newAccount.getUsername()) :
				accountRepository.findById(newAccount.getId());

		List<CodeDTO> errorCodes = verifyInput(newAccount,signup);
		
		// adding new user or changing username to one of already existing usernames
		if (oldAccount.isPresent() && (signup || newAccount.getId()!=oldAccount.get().getId())) {
			errorCodes.add(new CodeDTO("username", "Użytkownik o tym adresie email już istnieje"));
		}
		
		if (!errorCodes.isEmpty()) {
			response.setCode(errorCodes);
			response.setStatus("error");			
			return response;
		}
		
		if (signup || newAccount.getPassword()!=null) {
			newAccount.setPassword(stringService.hashPassword(newAccount.getPassword()));
		}
		
		if (signup) {
			newAccount.setHashLink(stringService.getLinkHash());
			emailService.signUp(newAccount);
		}
		else {
			newAccount.setHashLink(oldAccount.get().getHashLink());
			newAccount.setIsActivated(oldAccount.get().getIsActivated());
			if (newAccount.getType()==null) {
				newAccount.setType(oldAccount.get().getType());
			}
		}
		
		accountRepository.save(newAccount);

		response.setStatus("success");
		response.setCode(new ArrayList<CodeDTO>());
		response.setResponse(newAccount);		

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{username:.+}")
	public ResponseDTO readAccount(@PathVariable String username) {
		Optional<Account> account = accountRepository.findByUsername(username);
		Map<String,String> input = new HashMap<>();
		input.put("username", username);
		ResponseDTO response = new ResponseDTO(input);
		List<CodeDTO> errorCodes = new ArrayList<>();
		
		if (account.isPresent()) {
			response.setStatus("success");
			response.setResponse(account.get());
		}
		else {
			response.setStatus("error");
			errorCodes.add(new CodeDTO("username","Użytkownik o tym adresie email nie istnieje"));
		}
		response.setCode(errorCodes);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseDTO login(@RequestBody LoginForm input) {
		Optional<Account> account = accountRepository.findByUsername(input.getUsername());
		ResponseDTO response = new ResponseDTO(input);
		List<CodeDTO> errorCodes = new ArrayList<>();
		
		if (!account.isPresent() || !stringService.checkPass(input.getPassword(), account.get().getPassword())) {
			errorCodes.add(new CodeDTO("loginForm","Błędny użytkownik lub hasło"));
			response.setStatus("error");
		}
		else if (!account.get().getIsActivated()) {
			errorCodes.add(new CodeDTO("username","Konto nie zostało aktywowane"));
			response.setStatus("error");
		}
		else {
			response.setStatus("success");
			response.setResponse(account.get());
		}
		response.setCode(errorCodes);
		
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/reset/{username:.+}")
	public ResponseDTO resetPassword(@PathVariable String username) {
		Optional<Account> account = accountRepository.findByUsername(username);
		Map<String,String> input = new HashMap<>();
		input.put("username", username);
		ResponseDTO response = new ResponseDTO(input);
		List<CodeDTO> errorCodes = new ArrayList<>();
		
		if (account.isPresent()) {
	    	String newPassword = stringService.getNewPassword();
	    	emailService.resetPassword(account.get(),newPassword);
			account.get().setPassword(stringService.hashPassword(newPassword));
			accountRepository.save(account.get());
			
			response.setStatus("success");
			response.setResponse(account.get());
		}
		else {
			response.setStatus("error");
			errorCodes.add(new CodeDTO("username","Użytkownik o tym adresie email nie istnieje"));
		}
		response.setCode(errorCodes);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/activate/{username:.+}")
	public ResponseDTO activateAccount(@PathVariable String username, @RequestParam(value = "key") String key ) {
		Optional<Account> account = accountRepository.findByUsername(username);
		Map<String,String> input = new HashMap<>();
		input.put("username", username);
		input.put("key", key);
		ResponseDTO response = new ResponseDTO(input);
		List<CodeDTO> errorCodes = new ArrayList<>();
		
		if (account.isPresent() && account.get().getHashLink().equals(key)) {
			account.get().setIsActivated(true);
			accountRepository.save(account.get());
			
			response.setStatus("success");
			response.setResponse(account.get());
		}
		else if (account.isPresent()) {
			response.setStatus("error");
			errorCodes.add(new CodeDTO("key","Błędny klucz aktywacyjny"));
		}
		else {
			response.setStatus("error");
			errorCodes.add(new CodeDTO("username","Użytkownik o tym adresie email nie istnieje"));
		}
		response.setCode(errorCodes);
		return response;
	}

	private List<CodeDTO> verifyInput(Account input, boolean signup) {
		List<CodeDTO> result = new ArrayList<>();
		//required fields verification on signup
		if (signup) {
			if ("".equals(input.getUsername())) {
				result.add(new CodeDTO("username","Email jest polem wymaganym"));
			}
			if (input.getType()==null) {
				result.add(new CodeDTO("type","Typ jest polem wymaganym"));
			}
			if (input.getPassword()==null) {
				result.add(new CodeDTO("password","Haso jest polem wymaganym"));
			}
			if (input.getFirstName()==null) {
				result.add(new CodeDTO("firstName","Imię jest polem wymaganym"));
			}
			if (input.getLastName()==null) {
				result.add(new CodeDTO("lastName","Nazwisko jest polem wymaganym"));
			}
			if (input.getPhone()==null) {
				result.add(new CodeDTO("phone","Numer telefonu jest polem wymaganym"));
			}
			if (input.getDateOfBirth()==null) {
				result.add(new CodeDTO("dateOfBirth","Data urodzenia jest polem wymaganym"));
			}
			if (input.getCity()==null && input.getType()==Constants.TYPE_PRIVATE) {
				result.add(new CodeDTO("city","Miasto jest polem wymaganym"));
			}
			if (input.getPostCode()==null && input.getType()==Constants.TYPE_PRIVATE) {
				result.add(new CodeDTO("postCode","Kod pocztowy jest polem wymaganym"));
			}
			if (input.getStreet()==null && input.getType()==Constants.TYPE_PRIVATE) {
				result.add(new CodeDTO("street","Ulica jest polem wymaganym"));
			}
			if (input.getHouseNumber()==null && input.getType()==Constants.TYPE_PRIVATE) {
				result.add(new CodeDTO("houseNumber","Nr budynku jest polem wymaganym"));
			}
			if (input.getCompanyName()==null && input.getType()==Constants.TYPE_COMPANY) {
				result.add(new CodeDTO("companyName","Nazwa firmy jest polem wymaganym"));
			}
			if (input.getNip()==null && input.getType()==Constants.TYPE_COMPANY) {
				result.add(new CodeDTO("nip","NIP jest polem wymaganym"));
			}
		}
		
		//regex verification
		if (input.getUsername()!=null && !"".equals(input.getUsername()) && !Pattern.compile(Constants.EMAIL_PATTERN)
				.matcher(input.getUsername())
				.matches() ) {
			result.add(new CodeDTO("username","Email jest niepoprawny"));
		}
		if (input.getPassword()!=null && !Pattern.compile(Constants.PASSWORD_PATTERN)
				.matcher(input.getPassword())
				.matches() ) {
			result.add(new CodeDTO("password","Haso musi zawierać co najmniej jedną małą literę, "
					+ "jedną dużą literę, jedną cyfrę oraz jeden znak specjalny"));
		}
		if (input.getFirstName()!=null && !Pattern.compile(Constants.FIRSTNAME_PATTERN)
				.matcher(input.getFirstName())
				.matches() ) {
			result.add(new CodeDTO("firstName","Imię musi składać się z co najmniej 3 liter"));
		}
		if (input.getLastName()!=null && !Pattern.compile(Constants.LASTNAME_PATTERN)
				.matcher(input.getLastName())
				.matches() ) {
			result.add(new CodeDTO("lastName","Nazwisko musi składać się z co najmniej 3 liter"));
		}
		if (input.getPhone()!=null && !Pattern.compile(Constants.PHONE_PATTERN)
				.matcher(input.getPhone())
				.matches() ) {
			result.add(new CodeDTO("phone","Numer telefonu musi zawierać 9 cyfr"));
		}
		if (input.getDateOfBirth()!=null && !Pattern.compile(Constants.DATEOFBIRTH_PATTERN)
				.matcher(input.getDateOfBirth())
				.matches() ) {
			result.add(new CodeDTO("dateOfBirth","Podaj datę urodzenia w formacie rrrr-mm-dd"));
		}
		if (input.getHouseNumber()!=null && !Pattern.compile(Constants.HOUSENUMBER_PATTERN)
				.matcher(input.getHouseNumber())
				.matches() ) {
			result.add(new CodeDTO("houseNumber","Numer budynku może zawierać cyfry, litery oraz ukośniki"));
		}
		if (input.getCity()!=null && !Pattern.compile(Constants.CITY_PATTERN)
				.matcher(input.getCity())
				.matches() ) {
			result.add(new CodeDTO("city","Miasto musi się składać z co najmniej 3 liter"));
		}
		if (input.getPostCode()!=null && !Pattern.compile(Constants.POSTCODE_PATTERN)
				.matcher(input.getPostCode())
				.matches() ) {
			result.add(new CodeDTO("postCode","Podaj kod pocztowy w formacie xx-xxx"));
		}
		if (input.getStreet()!=null && !Pattern.compile(Constants.STREET_PATTERN)
				.matcher(input.getStreet())
				.matches() ) {
			result.add(new CodeDTO("street","Ulica musi sklładać się z co najmniej 3 znaków, poza literami dozwolone są spacja, apostrof, kropka, cudzysłów"));
		}
		if (input.getCompanyName()!=null && !Pattern.compile(Constants.COMPANYNAME_PATTERN)
				.matcher(input.getCompanyName())
				.matches() ) {
			result.add(new CodeDTO("companyName","Nazwa firmy musi składać się z co najmniej 3 znaków i nie zawierać średnika"));
		}
		if (input.getNip()!=null && !Pattern.compile(Constants.NIP_PATTERN)
				.matcher(input.getNip())
				.matches() ) {
			result.add(new CodeDTO("nip","NIP musi zawierać 10 cyfr"));
		}
		else if (input.getNip()!=null && !stringService.isNIPValid(input.getNip())) {
			result.add(new CodeDTO("nip","Numer NIP jest niepoprawny"));
		}
		
		return result;
	}
}
