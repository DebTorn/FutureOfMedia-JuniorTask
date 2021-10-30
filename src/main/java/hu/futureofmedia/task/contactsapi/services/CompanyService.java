package hu.futureofmedia.task.contactsapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;

@Service
public class CompanyService {

	private CompanyRepository repo;

	@Autowired
	public CompanyService(CompanyRepository repo) {
		this.repo = repo;
	}
	
	
	
}
