package hu.futureofmedia.task.contactsapi.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import hu.futureofmedia.task.contactsapi.entities.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long>{
	List<Contact> findAll(Pageable pagination);
}
