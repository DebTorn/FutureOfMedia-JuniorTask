package hu.futureofmedia.task.contactsapi.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	private final int ERROR_STATUS_CODE = 420;
	
	@Autowired
	public void setServices(ContactService contactService, CompanyService companyService) 
	{
		this.contactService = contactService;
		this.companyService = companyService;
	}
	
	@GetMapping("/contact/list/{page}")
	public @ResponseBody ResponseEntity<List<Contact>> ListContacts(@PathVariable(value="page") int page)
	{
		return ResponseEntity.ok(contactService.FindAll(page));
	}
	
	@GetMapping("/contact/{id}")
	public @ResponseBody ResponseEntity<Object> GetContact(@PathVariable(value="id") Long id)
	{
		return ResponseEntity.ok(contactService.FindById(id));
	}
	
	@GetMapping("/contact/delete/{id}")
	public @ResponseBody BodyBuilder DeleteContact(@PathVariable(value="id") Long id)
	{		
		if(contactService.Delete(id)) 
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED);
		}
		
		return ResponseEntity.status(ERROR_STATUS_CODE);
	}
	
	@GetMapping("/companies")
	public ResponseEntity<List<Company>> Companies()
	{
		return ResponseEntity.ok(companyService.FindAll());
	}
	
	@PostMapping("/contact/new")
	public @ResponseBody BodyBuilder CreateContact(@RequestParam Map<String, String> Datas)
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
		
		if(db == 0 || Datas != null || Datas.size() != 0) 
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
			System.out.println(contact.EmailValidation());
			System.out.println(contact.TelefonszamValidation());
			if(!contact.EmailValidation() 
					|| !contact.TelefonszamValidation() 
					|| company == null
			) 
			{
				return ResponseEntity.status(ERROR_STATUS_CODE);
			}
			
			contact.setCeg(company);
			contact.setCreated_at(new Timestamp(new Date().getTime()));
			
			if(contactService.Save(contact)) {
				return ResponseEntity.status(HttpStatus.ACCEPTED);
			}
			
			return ResponseEntity.status(ERROR_STATUS_CODE);
		
		}
		
		return ResponseEntity.status(ERROR_STATUS_CODE);
		
	}
	
	@PostMapping("/contact/update")
	public @ResponseBody BodyBuilder UpdateContact(@RequestParam Map<String, String> Datas)
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
		
		if(db == 0 || Datas != null || Datas.size() != 0) 
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
					|| !contact.TelefonszamValidation() 
					|| company == null
			) 
			{
				return ResponseEntity.status(ERROR_STATUS_CODE);
			}
			
			contact.setCeg(company);
			contact.setUpdated_at(new Timestamp(new Date().getTime()));
			
			if(contactService.Save(contact)) {
				return ResponseEntity.status(HttpStatus.ACCEPTED);
			}
			
			return ResponseEntity.status(ERROR_STATUS_CODE);
		
		}
		
		return ResponseEntity.status(ERROR_STATUS_CODE);
		
	}
	
}
