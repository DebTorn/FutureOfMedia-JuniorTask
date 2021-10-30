package hu.futureofmedia.task.contactsapi.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import hu.futureofmedia.task.contactsapi.services.interfaces.ContactServiceInterface;

@Service
public class ContactService implements ContactServiceInterface{

	private ContactRepository repo;

	@Autowired
	public ContactService(ContactRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<JSONObject> FindAll(int PageNumber) {
		Pageable pagination = PageRequest.of(PageNumber, PageNumber+10, Sort.by("vezeteknev").ascending());
		
		List<Contact> contacts = repo.findAll(pagination);
		List<JSONObject> jsonContacts = new ArrayList<>();
		
		for(Contact contact : contacts) {
			jsonContacts.add(contact.toHalfJSON());
		}
		
		return jsonContacts;
	}

	@Override
	public Contact FindById(Long id) {
		return repo.findById(id);
	}

	@Override
	public boolean Delete(Long id) {
		return repo.deleteById(id);
	}
	
}
