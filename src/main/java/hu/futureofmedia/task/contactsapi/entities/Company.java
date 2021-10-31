package hu.futureofmedia.task.contactsapi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private Set<Contact> contacts;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", contacts=" + contacts + "]";
	}
    
}
