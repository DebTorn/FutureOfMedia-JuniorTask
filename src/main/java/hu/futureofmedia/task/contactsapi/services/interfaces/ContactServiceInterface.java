package hu.futureofmedia.task.contactsapi.services.interfaces;

import java.util.List;

import hu.futureofmedia.task.contactsapi.entities.Contact;

public interface ContactServiceInterface {
	public List<Contact> FindAll(int PageNumber);
	public Contact FindById(Long id);
	public boolean Delete(Long id);
	public boolean Save(Contact contact);
}
