package hu.futureofmedia.task.contactsapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.others.StatuszType;
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
	public Contact FindById(Long id) {
		Optional<Contact> contact = repo.findById(id);
		if(contact.isPresent()) {
			return repo.findById(id).get();
		}
		return null;
	}

	@Override
	public boolean Delete(Long id) {
		Optional<Contact> contact = repo.findById(id);
		
		if(contact.isPresent()) {
			contact.get().setStatusz(StatuszType.TOROLT);
			if(repo.save(contact.get()) != null) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean Save(Contact contact) {
		if(repo.save(contact) != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<Contact> findByStatusz(String statusz, int page, int size) {
		List<Contact> contacts = repo.findByStatusz(statusz, page, size);
		
		return contacts;
	}

	@Override
	public boolean existsByEmail(String email) {
		if(repo.existsByEmail(email)) {
			return true;
		}
		return false;
	}
	
}
