package hu.futureofmedia.task.contactsapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.repositories.CompanyRepository;
import hu.futureofmedia.task.contactsapi.services.interfaces.CompanyServiceInterface;

@Service
public class CompanyService implements CompanyServiceInterface{

	private CompanyRepository repo;

	@Autowired
	public CompanyService(CompanyRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Company> FindAll() {
		return repo.findAll();
	}

	@Override
	public Company FindById(Long id) {
		return repo.findById(id);
	}
	
	
	
}
