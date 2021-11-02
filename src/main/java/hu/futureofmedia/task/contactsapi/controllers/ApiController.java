package hu.futureofmedia.task.contactsapi.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.others.StatuszType;
import hu.futureofmedia.task.contactsapi.services.CompanyService;
import hu.futureofmedia.task.contactsapi.services.ContactService;

@RestController
@RequestMapping("/api/v1")
public class ApiController 
{
	
	private ContactService contactService;
	private CompanyService companyService;
	private final HttpStatus ERROR_STATUS_CODE = HttpStatus.BAD_REQUEST;
	
	@Autowired
	public void setServices(ContactService contactService, CompanyService companyService) 
	{
		this.contactService = contactService;
		this.companyService = companyService;
	}
	
	@GetMapping("/contact/list")
	public @ResponseBody ResponseEntity<List<Contact>> ListContacts(@RequestParam(name="page") int page)
	{
		List<Contact> contacts = contactService.findByStatusz(StatuszType.AKTIV.toString(), (page-1)*10, 10);
		
		return new ResponseEntity<>(contacts, HttpStatus.OK);
	}
	
	@GetMapping("/contact/{id}")
	public @ResponseBody ResponseEntity<Object> GetContact(@PathVariable(value="id") Long id)
	{
		return ResponseEntity.ok(contactService.FindById(id));
	}
	
	@GetMapping("/contact/delete/{id}")
	public void DeleteContact(@PathVariable(value="id") Long id)
	{		
		if(contactService.Delete(id)) 
		{
			throw new ResponseStatusException(HttpStatus.OK, "Sikeres törlés");
		}
		
		throw new ResponseStatusException(ERROR_STATUS_CODE, "Sikertelen törlés");
	}
	
	@GetMapping("/companies")
	public @ResponseBody ResponseEntity<List<Company>> Companies()
	{
		return ResponseEntity.ok(companyService.FindAll());
	}
	
	@PostMapping("/contact/new")
	public void CreateContact(@RequestParam Map<String, String> Datas, @RequestBody String body)
	{
		System.out.println("req body: "+ body);
		int db = 0;
		
		for(Map.Entry<String, String> data : Datas.entrySet()) 
		{
			if(!data.getKey().equalsIgnoreCase("telefonszam")) //opcionális adatok kivételével
			{
				if(data.getValue().isEmpty()) 
				{
					db++;
				}	
			}
		}

		if(db == 0 && Datas != null && Datas.size() != 0) 
		{
			Contact contact = new Contact
			(
					Datas.get("vezeteknev"), 
					Datas.get("keresztnev"), 
					Datas.get("email"), 
					Datas.get("telefonszam"), 
					null,
					Datas.get("megjegyzes"), 
					StatuszType.AKTIV
			);
			Company company = companyService.FindById(Long.parseLong(Datas.get("company")));
			
			
			
			if(!contact.EmailValidation() 
					|| contact.getTelefonszam().isEmpty() ? false : !contact.TelefonszamValidation()
			) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Az e-mail cím vagy a telefonszám formátuma hibás");
			}
			
			if(contactService.existsByEmail(contact.getEmail())) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Ezzel az e-mail címmel már szerepel kapcsolattartó az adatbázisban");
			}
			
			if(!contact.getTelefonszam().isEmpty()
				&& contactService.existsByTelefonszam(contact.getTelefonszam())) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Ezzel a telefonszámmal már szerepel kapcsolattartó az adatbázisban");
			}
			
			if(company == null) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Csak valós cég adható meg");
			}
			
			contact.setCeg(company);
			contact.setCreated_at(new Timestamp(new Date().getTime()));
			
			if(contactService.Save(contact)) {
				throw new ResponseStatusException(HttpStatus.OK, "Sikeres feltöltés");
			}
			
			throw new ResponseStatusException(ERROR_STATUS_CODE, "Sikertelen feltöltés");
		
		}
		
		throw new ResponseStatusException(ERROR_STATUS_CODE, "Az adatok megadása kötelező");
		
	}
	
	@PostMapping("/contact/update")
	public void UpdateContact(@RequestParam Map<String, String> Datas)
	{
		int db = 0;
		
		for(Map.Entry<String, String> data : Datas.entrySet()) 
		{
			if(!data.getKey().equalsIgnoreCase("telefonszam")) //opcionális adatok kivételével
			{
				if(data.getValue().isEmpty()) 
				{
					db++;
				}	
			}
		}
		
		if(db == 0 && Datas != null && Datas.size() != 0) 
		{
			
			Contact contact = contactService.FindById(Long.parseLong(Datas.get("id")));
			contact.setVezeteknev(Datas.get("vezeteknev"));
			contact.setKeresztnev(Datas.get("keresztnev"));
			contact.setMegjegyzes(Datas.get("megjegyzes"));
			
			Company company = companyService.FindById(Long.parseLong(Datas.get("company")));
			
			if(!contact.EmailValidation() 
					|| contact.getTelefonszam().isEmpty() ? false : !contact.TelefonszamValidation()
			) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Az e-mail cím vagy a telefonszám formátuma hibás");
			}
			//Contact indb = contactService.FindById(contact.getId());
			
			if(!contact.getEmail().equals(Datas.get("email")) && contactService.existsByEmail(Datas.get("email"))) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Ezzel az e-mail címmel már szerepel kapcsolattartó az adatbázisban");
			}
			
			contact.setEmail(Datas.get("email"));
			
			if(!Datas.get("telefonszam").isEmpty() && !contact.getTelefonszam().equals(Datas.get("telefonszam")) && contactService.existsByTelefonszam(Datas.get("telefonszam"))) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Ezzel a telefonszámmal már szerepel kapcsolattartó az adatbázisban");
			}
			
			contact.setTelefonszam(Datas.get("telefonszam"));
			
			if(company == null) 
			{
				throw new ResponseStatusException(ERROR_STATUS_CODE, "Csak valós cég adható meg");
			}
			
			contact.setCeg(company);
			contact.setUpdated_at(new Timestamp(new Date().getTime()));
			
			if(contactService.Save(contact)) {
				throw new ResponseStatusException(HttpStatus.OK, "Sikeres feltöltés");
			}
			
			throw new ResponseStatusException(ERROR_STATUS_CODE, "Sikertelen feltöltés");
		
		}
		
		throw new ResponseStatusException(ERROR_STATUS_CODE, "Az adatok megadása kötelező");
		
	}
	
}
