package com.WSBGroupProject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.WSBGroupProject.repository.AccountRepository;
import com.WSBGroupProject.repository.ServiceRepository;
import com.WSBGroupProject.model.Account;
import com.WSBGroupProject.model.Service;
import com.WSBGroupProject.dto.*;
import com.WSBGroupProject.constants.Constants;

@RestController
@RequestMapping("/services")
public class ServiceRestController {
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	AccountRepository accountRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseDTO getServices() {
		ResponseDTO response = new ResponseDTO("success",
				new ArrayList<CodeDTO>(),
				"",
				serviceRepository.findAll());
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseDTO postAccount(@RequestBody ServiceForm input) {
		ResponseDTO response = new ResponseDTO(input);
		boolean isNew = (input.getId() == null);
		Optional<Account> poster = accountRepository.findById(input.getUserId());
		Service newService = new Service(input, poster.orElse(null));
		Service oldService = serviceRepository.findById(input.getId()).orElse(null);

		List<CodeDTO> errorCodes = verifyInput(newService,isNew);

		if (!isNew && oldService==null) {
			errorCodes.add(new CodeDTO("id", "Nie ma takiej oferty"));
		}
		
		if (!errorCodes.isEmpty()) {
			response.setCode(errorCodes);
			response.setStatus("error");			
			return response;
		}
		
		if (!isNew) {
			mergeOldDataIntoNewService(newService, oldService);
		}
		else {
			newService.setStatus(Constants.STATUS_AVAILABLE);
		}
		
		serviceRepository.save(newService);

		response.setStatus("success");
		response.setCode(new ArrayList<CodeDTO>());
		response.setResponse(newService);		

		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/updateStatus")
	public ResponseDTO update(@RequestParam(value = "serviceId") Long serviceId, 
			@RequestParam(value = "status") Integer status, @RequestParam(value = "userId") Long userId) {
		Optional<Service> service = serviceRepository.findById(serviceId);
		Optional<Account> account = accountRepository.findById(userId);
		Map<String,Long> input = new HashMap<>();
		input.put("serviceId", serviceId);
		input.put("status", status.longValue());
		input.put("userId", userId);
		ResponseDTO response = new ResponseDTO(input);
		List<CodeDTO> errorCodes = new ArrayList<>();
		
		if (!service.isPresent()) {
			errorCodes.add(new CodeDTO("serviceId","Oferta o tym identyfikatorze nie istnieje"));
		}
		if (userId!=null && !account.isPresent()) {
			errorCodes.add(new CodeDTO("userId","Brak użytkownika o tym identyfikatorze"));
		}
		if (status==null || (status<Constants.STATUS_AVAILABLE || status>Constants.STATUS_CANCELED)) {
			errorCodes.add(new CodeDTO("status","Niepoprawna wartość statusu"));
		}
		//reservation
		if (service.isPresent() && service.get().getStatus()==Constants.STATUS_AVAILABLE 
				&& account.isPresent() && status==Constants.STATUS_ORDERED) {
			if (service.get().getType()==Constants.SERVICE_PROVIDER) {
				service.get().setRecipient(account.get());
			}
			else if (service.get().getType()==Constants.SERVICE_RECIPIENT) {
				service.get().setProvider(account.get());
			}
			service.get().setStatus(Constants.STATUS_ORDERED);
		}
		//cancelation by the user who accepted offer or by the user who posted the offer
		else if (service.isPresent() && status==Constants.STATUS_CANCELED && account.isPresent() 
				&& service.get().getStatus()!=Constants.STATUS_DONE){
			if (service.get().getType()==Constants.SERVICE_RECIPIENT && account.get().getId()==service.get().getProvider().getId()
					&& service.get().getStatus()==Constants.STATUS_ORDERED) {
				service.get().setProvider(null);
				service.get().setStatus(Constants.STATUS_AVAILABLE);
			}
			else if (service.get().getType()==Constants.SERVICE_PROVIDER && account.get().getId()==service.get().getRecipient().getId()
					&& service.get().getStatus()==Constants.STATUS_ORDERED) {
				service.get().setRecipient(null);
				service.get().setStatus(Constants.STATUS_AVAILABLE);
			}
			else if (service.get().getType()==Constants.SERVICE_RECIPIENT && account.get().getId()==service.get().getRecipient().getId()) {
				service.get().setProvider(null);
				service.get().setStatus(Constants.STATUS_CANCELED);
			}
			else if (service.get().getType()==Constants.SERVICE_PROVIDER && account.get().getId()==service.get().getProvider().getId()) {
				service.get().setRecipient(null);
				service.get().setStatus(Constants.STATUS_CANCELED);
			}
		}
		//confirmation
		else if (service.isPresent() && status==Constants.STATUS_DONE && account.isPresent() && service.get().getStatus()==Constants.STATUS_ORDERED){
			if (account.get().getId()==service.get().getProvider().getId() || account.get().getId()==service.get().getRecipient().getId()) {
				service.get().setStatus(Constants.STATUS_DONE);
			}
		}
		//restoration
		else if (service.isPresent() && status==Constants.STATUS_AVAILABLE && account.isPresent() && service.get().getStatus()==Constants.STATUS_CANCELED){
			if ((service.get().getType()==Constants.SERVICE_RECIPIENT && account.get().getId()==service.get().getRecipient().getId()) 
					|| (service.get().getType()==Constants.SERVICE_PROVIDER && account.get().getId()==service.get().getProvider().getId())) {
				service.get().setStatus(Constants.STATUS_AVAILABLE);
			}
		}
		else {
			errorCodes.add(new CodeDTO("updateStatus","Podano błędne parametry"));
		}
		
		if (errorCodes.size() > 0) {
			response.setStatus("error");
		}
		else {
			serviceRepository.save(service.get());
			response.setStatus("success");
			response.setResponse(service.get());
		}		
		response.setCode(errorCodes);		
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public ResponseDTO search(@RequestBody ServiceForm input) {
		List<Service> services = serviceRepository.findAll();
		ResponseDTO response = new ResponseDTO(input);
		List<CodeDTO> errorCodes = new ArrayList<>();

		List<Service> filteredServices = services.stream()
				.filter(s -> {return (input.getCategory()==null || StringUtils.containsIgnoreCase(s.getCategory(), input.getCategory()))
						&& (input.getName()==null || StringUtils.containsIgnoreCase(s.getName(), input.getName()))
						&& (input.getAmount()==null || Float.parseFloat(s.getAmount())<Float.parseFloat(input.getAmount().replace(",", ".").replaceAll("[^\\d\\.]", "")))
						&& (input.getStreet()==null || StringUtils.containsIgnoreCase(s.getStreet(), input.getStreet()))
						&& (input.getHouseNumber()==null || StringUtils.containsIgnoreCase(s.getHouseNumber(), input.getHouseNumber()))
						&& (input.getCity()==null || StringUtils.containsIgnoreCase(s.getCity(), input.getCity()))
						&& (input.getPostCode()==null || s.getPostCode().contains(input.getPostCode()))
						&& (input.getDateTime()==null || s.getDateTime().contains(input.getDateTime()))
						&& (input.getStatus()==null || s.getStatus().equals(input.getStatus()))
						&& (input.getType()==null || s.getType().equals(input.getType()))
						&& (input.getUserId()==null || (s.getProvider()!=null && s.getProvider().getId().equals(input.getUserId())) || (s.getRecipient()!=null && s.getRecipient().getId().equals(input.getUserId())))
						;})
				.collect(Collectors.toList());
		
		if (filteredServices.size() > 0) {
			response.setStatus("success");
		}
		else {
			response.setStatus("error");
			errorCodes.add(new CodeDTO("result","Brak ofert spełniających podane kryteria"));
		}
		response.setResponse(filteredServices);
		response.setCode(errorCodes);
		
		return response;
	}

	private List<CodeDTO> verifyInput(Service input, boolean isNew) {
		List<CodeDTO> result = new ArrayList<>();
		//required fields verification on posting new Service
		if (isNew) {
			if (input.getType()==null) {
				result.add(new CodeDTO("type","Typ jest polem wymaganym"));
			}
			if (input.getCategory()==null) {
				result.add(new CodeDTO("category","Kategoria jest polem wymaganym"));
			}
			if (input.getName()==null) {
				result.add(new CodeDTO("name","Nazwa jest polem wymaganym"));
			}
			if (input.getAmount()==null) {
				result.add(new CodeDTO("amount","Ilość jest polem wymaganym"));
			}
			if (input.getDescription()==null) {
				result.add(new CodeDTO("description","Opis jest polem wymaganym"));
			}
			if (input.getCity()==null) {
				result.add(new CodeDTO("city","Miasto jest polem wymaganym"));
			}
			if (input.getPostCode()==null) {
				result.add(new CodeDTO("postCode","Kod pocztowy jest polem wymaganym"));
			}
			if (input.getStreet()==null) {
				result.add(new CodeDTO("street","Ulica jest polem wymaganym"));
			}
			if (input.getHouseNumber()==null) {
				result.add(new CodeDTO("houseNumber","Nr budynku jest polem wymaganym"));
			}
			if (input.getDateTime()==null) {
				result.add(new CodeDTO("dateTime","Data i godzina oferty jest polem wymaganym"));
			}
		}
		
		//regex verification
		if (input.getAmount()!=null && !Pattern.compile(Constants.FLOAT_PATTERN)
				.matcher(input.getAmount())
				.matches() ) {
			result.add(new CodeDTO("amount","Podaj kwotę z maksymalnie 2 miejscami po przecinku"));
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
		
		return result;
	}
	
	private void mergeOldDataIntoNewService(Service newService, Service oldService) {
		newService.setStatus(oldService.getStatus());
		newService.setProvider(oldService.getProvider());
		newService.setRecipient(oldService.getRecipient());

		if (newService.getType()==null) {
			newService.setType(oldService.getType());
		}
		if (newService.getCategory()==null) {
			newService.setCategory(oldService.getCategory());
		}
		if (newService.getName()==null) {
			newService.setName(oldService.getName());
		}
		if (newService.getAmount()==null) {
			newService.setAmount(oldService.getAmount());
		}
		if (newService.getDescription()==null) {
			newService.setDescription(oldService.getDescription());
		}
		if (newService.getCity()==null) {
			newService.setCity(oldService.getCity());
		}
		if (newService.getPostCode()==null) {
			newService.setPostCode(oldService.getPostCode());
		}
		if (newService.getStreet()==null) {
			newService.setStreet(oldService.getStreet());
		}
		if (newService.getHouseNumber()==null) {
			newService.setHouseNumber(oldService.getHouseNumber());
		}
		if (newService.getDateTime()==null) {
			newService.setDateTime(oldService.getDateTime());
		}
	}
}
