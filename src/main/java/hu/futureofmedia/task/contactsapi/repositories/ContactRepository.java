package hu.futureofmedia.task.contactsapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hu.futureofmedia.task.contactsapi.entities.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long>{
	@Query(value="SELECT * FROM contacts WHERE statusz LIKE :statusz ORDER BY vezeteknev ASC LIMIT :size OFFSET :page", nativeQuery=true)
	List<Contact> findByStatusz(@Param("statusz") String statusz,@Param("page") int page, @Param("size") int size);
	
	boolean existsByEmail(String email);
}
