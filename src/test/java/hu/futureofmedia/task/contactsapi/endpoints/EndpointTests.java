package hu.futureofmedia.task.contactsapi.endpoints;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.futureofmedia.task.contactsapi.controllers.ApiController;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.others.StatuszType;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import hu.futureofmedia.task.contactsapi.services.CompanyService;
import hu.futureofmedia.task.contactsapi.services.ContactService;

@WebMvcTest(ApiController.class)
public class EndpointTests {
	
	private final String apiVersion = "/api/v1";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper om;
	
	@MockBean
	private ContactService contactService;
	
	@MockBean
	private CompanyService companyService;
	
	@MockBean
	private ContactRepository contactRepository;
	
	@Test
	public void TestNewContactWhenEmailIsExist() throws Exception {
		String datas = "vezeteknev=TesztVezeteknev1"
				+ "&keresztnev=TesztKeresztnev1"
				+ "&email=teszt.teszt1@gmail.com"
				+ "&telefonszam=%2B36209941231"
				+ "&company=1"
				+ "&megjegyzes=Ez egy random megjegyzés";
		
		Company company = new Company("Company 1");
		company.setId(1l);
		
		Contact newContactEntity = new Contact(
				"TesztVezeteknev1", 
				"TesztKeresztnev1", 
				"teszt.teszt1@gmail.com", 
				"+36209941231", 
				null,
				"Ez egy random megjegyzés",
				StatuszType.AKTIV
				);
		
		newContactEntity.setCeg(company);
		newContactEntity.setCreated_at(new Timestamp(new Date().getTime()));
		
		Mockito.when(companyService.FindById(1l)).thenReturn(company);
		Mockito.when(contactService.existsByEmail("teszt.teszt1@gmail.com")).thenReturn(true);
		
		MvcResult result = mvc.perform(post(apiVersion+"/contact/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(datas))
				.andExpect(status().is(400)).andReturn();
		
		assertEquals("Ezzel az e-mail címmel már szerepel kapcsolattartó az adatbázisban", result.getResponse().getErrorMessage());
		
	}
	
	@Test
	public void TestNewContactWhenPhonenumberIsExist() throws Exception {
		String datas = "vezeteknev=TesztVezeteknev1"
				+ "&keresztnev=TesztKeresztnev1"
				+ "&email=teszt.teszt1@gmail.com"
				+ "&telefonszam=%2B36209941231"
				+ "&company=1"
				+ "&megjegyzes=Ez egy random megjegyzés";
		
		Company company = new Company("Company 1");
		company.setId(1l);
		
		Contact newContactEntity = new Contact(
				"TesztVezeteknev1", 
				"TesztKeresztnev1", 
				"teszt.teszt1@gmail.com", 
				"+36209941231", 
				null,
				"Ez egy random megjegyzés",
				StatuszType.AKTIV
				);
		
		newContactEntity.setCeg(company);
		newContactEntity.setCreated_at(new Timestamp(new Date().getTime()));
		
		Mockito.when(companyService.FindById(1l)).thenReturn(company);
		Mockito.when(contactService.existsByTelefonszam("+36209941231")).thenReturn(true);
		
		MvcResult result = mvc.perform(post(apiVersion+"/contact/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(datas))
				.andExpect(status().is(400)).andReturn();
		
		assertEquals("Ezzel a telefonszámmal már szerepel kapcsolattartó az adatbázisban", result.getResponse().getErrorMessage());
		
	}
	
	@Test
	public void TestNewContactWhenDataNotSend() throws JsonProcessingException, Exception {
		
		String datas = "vezeteknev=TesztVezeteknev1"
				+ "&keresztnev=TesztKeresztnev1"
				+ "&email=teszt.teszt1@gmail.com"
				+ "&telefonszam=%2B36209941231"
				+ "&company=1"
				+ "&megjegyzes=Ez egy random megjegyzés";
		
		MvcResult result = mvc.perform(post(apiVersion+"/contact/new")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().is(400)).andReturn();
		
	}
	
	@Test
	public void TestList() throws Exception {
		//]
		List<Contact> contacts = new ArrayList<>();
		contacts.add(new Contact(
				"TesztVezeteknev1", 
				"TesztKeresztnev1", 
				"teszt-teszt.1@gmail.com", 
				"+36209941231", 
				companyService.FindById(1l),
				"Ez egy random cucc", 
				StatuszType.AKTIV
				));
		contacts.add(new Contact(
				"TesztVezeteknev2", 
				"TesztKeresztnev2", 
				"teszt-teszt.2@gmail.com", 
				"+36209941232", 
				companyService.FindById(2l),
				"Ez egy random cucc", 
				StatuszType.AKTIV
				));
		contacts.add(new Contact(
				"TesztVezeteknev3", 
				"TesztKeresztnev3", 
				"teszt-teszt.3@gmail.com", 
				"+36209941233", 
				companyService.FindById(3l),
				"Ez egy random cucc", 
				StatuszType.AKTIV
				));
		Mockito.when(contactService.findByStatusz(StatuszType.AKTIV.toString(), 0, 10)).thenReturn(contacts);
		
		
		MvcResult result = mvc.perform(get(apiVersion+"/contact/list?page=1"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		String responseJson = result.getResponse().getContentAsString();
		String expectedJson = om.writeValueAsString(contacts);
		
		assertEquals(expectedJson, responseJson);
		
	}
	
	@Test
	public void TestGetContact() throws Exception {
		Contact contact = new Contact(
				"TesztVezeteknev1", 
				"TesztKeresztnev1", 
				"teszt-teszt.1@gmail.com", 
				"+36209941231", 
				companyService.FindById(1l),
				"Ez egy random cucc", 
				StatuszType.AKTIV
				);
		
		Mockito.when(contactService.FindById(1l)).thenReturn(contact);
		
		MvcResult result = mvc.perform(get(apiVersion+"/contact/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		String responseJson = result.getResponse().getContentAsString();
		String expectedJson = om.writeValueAsString(contact);
		
		assertEquals(expectedJson, responseJson);
		
	}
	
	@Test
	public void TestCompanies() throws Exception {
		List<Company> companies = new ArrayList<>();
		companies.add(new Company(
				"Company 1"
			));
		companies.add(new Company(
				"Company 2"
			));
		companies.add(new Company(
				"Company 3"
			));
		
		Mockito.when(companyService.FindAll()).thenReturn(companies);
		
		MvcResult result = mvc.perform(get(apiVersion+"/companies"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		String responseJson = result.getResponse().getContentAsString();
		String expectedJson = om.writeValueAsString(companies);
		
		assertEquals(expectedJson, responseJson);
		
	}
	
	
	
}
