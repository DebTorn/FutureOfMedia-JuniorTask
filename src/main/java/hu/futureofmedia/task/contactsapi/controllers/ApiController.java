package hu.futureofmedia.task.contactsapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hu.futureofmedia.task.contactsapi.services.ContactService;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
	
	private ContactService contactService;
	
	@Autowired
	public void setServices(ContactService contactService) {
		this.contactService = contactService;
	}
	
	@GetMapping("/contact/list/{page}")
	public @ResponseBody ResponseEntity<List<JSONObject>> ListContacts(@PathVariable(value="page") int page){
		if(page <= -1) {
			throw new NullPointerException("A page nem lehet kisebb mint 0");
		}

		return ResponseEntity.ok(contactService.FindAll(page));
	}
	
	@GetMapping("/contact/{id}")
	public @ResponseBody ResponseEntity<JSONObject> GetContact(@PathVariable(value="id") Long id){
		if(id == null) {
			throw new NullPointerException("Az id nem lehet null");
		}
		
		return ResponseEntity.ok(contactService.FindById(id).toFullJSON());
	}
	
	@GetMapping("/contact/delete/{id}")
	public @ResponseBody ResponseEntity<JSONObject> DeleteContact(@PathVariable(value="id") Long id){
		if(id == null) {
			throw new NullPointerException("Az id nem lehet null");
		}
		String msg = "Sikertelen törlés";
		
		if(contactService.Delete(id)) {
			msg = "Sikeres törlés";
		}
		
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		
		return ResponseEntity.ok(json);
	}
	
}
