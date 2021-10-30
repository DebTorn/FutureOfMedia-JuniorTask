package hu.futureofmedia.task.contactsapi.services.interfaces;

import java.util.List;

import org.json.JSONObject;

import hu.futureofmedia.task.contactsapi.entities.Contact;

public interface ContactServiceInterface {
	public List<JSONObject> FindAll(int PageNumber);
	public Contact FindById(Long id);
	public boolean Delete(Long id);
}
