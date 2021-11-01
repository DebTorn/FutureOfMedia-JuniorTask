package hu.futureofmedia.task.contactsapi.services.interfaces;

import java.util.List;

import hu.futureofmedia.task.contactsapi.entities.Contact;

public interface ContactServiceInterface {
	public List<Contact> findByStatusz(String statusz, int page, int size);
	public Contact FindById(Long id);
	public boolean Delete(Long id);
	public boolean Save(Contact contact);
	public boolean existsByEmail(String email);
	public boolean existsByTelefonszam(String telefonszam);
}
