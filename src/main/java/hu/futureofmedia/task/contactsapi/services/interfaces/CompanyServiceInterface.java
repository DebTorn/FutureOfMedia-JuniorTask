package hu.futureofmedia.task.contactsapi.services.interfaces;

import java.util.List;

import hu.futureofmedia.task.contactsapi.entities.Company;

public interface CompanyServiceInterface {
	public List<Company> FindAll();
	public Company FindById(Long id);
}
