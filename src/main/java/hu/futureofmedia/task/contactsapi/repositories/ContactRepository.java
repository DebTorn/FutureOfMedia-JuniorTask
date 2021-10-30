package hu.futureofmedia.task.contactsapi.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.futureofmedia.task.contactsapi.entities.Contact;

public interface ContactRepository extends Repository<Contact, Long>{
	List<Contact> findAll(Pageable pagination);
	Contact findById(Long id);
	
	@Transactional
	boolean deleteById(Long id);
}
