package hu.futureofmedia.task.contactsapi.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.json.JSONObject;

@Entity
@Table(name="contacts")
public class Contact {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String vezeteknev;
	
	@Column(nullable=false)
	private String keresztnev;
	
	@Column(unique=true)
	private String email;
	
	@Column(unique=true)
	private String telefonszam;
	
	@ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	private Company ceg;
	
	private String megjegyzes;
	private Enum statusz;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column( nullable=false, columnDefinition="Timestamp default CURRENT_TIMESTAMP")
	private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column( nullable=true, columnDefinition="Timestamp default CURRENT_TIMESTAMP" )
	private Date updated_at;
	
	public Contact() {}
	
	public Contact(
			String vezeteknev, 
			String keresztnev, 
			String email, 
			String telefonszam, 
			Company ceg,
			String megjegyzes, 
			Enum statusz) 
	{
		this.vezeteknev = vezeteknev;
		this.keresztnev = keresztnev;
		this.email = email;
		this.telefonszam = telefonszam;
		this.ceg = ceg;
		this.megjegyzes = megjegyzes;
		this.statusz = statusz;
	}

	public String getVezeteknev() {
		return vezeteknev;
	}

	public void setVezeteknev(String vezeteknev) {
		this.vezeteknev = vezeteknev;
	}

	public String getKeresztnev() {
		return keresztnev;
	}

	public void setKeresztnev(String keresztnev) {
		this.keresztnev = keresztnev;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonszam() {
		return telefonszam;
	}

	public void setTelefonszam(String telefonszam) {
		this.telefonszam = telefonszam;
	}

	public Company getCeg() {
		return ceg;
	}

	public void setCeg(Company ceg) {
		this.ceg = ceg;
	}

	public String getMegjegyzes() {
		return megjegyzes;
	}

	public void setMegjegyzes(String megjegyzes) {
		this.megjegyzes = megjegyzes;
	}

	public Enum getStatusz() {
		return statusz;
	}

	public void setStatusz(Enum statusz) {
		this.statusz = statusz;
	}
	
	public JSONObject toHalfJSON() {
		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("tejes_nev", vezeteknev+" "+keresztnev);
		json.put("email", email);
		json.put("telefonszam", telefonszam);
		json.put("ceg_neve", ceg.getName());
		
		return json;
	}
	
	public JSONObject toFullJSON() {
		JSONObject json = new JSONObject();
		
		json.put("id", id);
		json.put("vezeteknev", vezeteknev);
		json.put("keresztnev", keresztnev);
		json.put("email", email);
		json.put("telefonszam", telefonszam);
		json.put("ceg_neve", ceg.getName());
		json.put("megjegyzes", megjegyzes);
		json.put("created_at", created_at);
		json.put("updated_at", updated_at);
		
		return json;
	}

	@Override
	public String toString() {
		return "Contact [vezeteknev=" + vezeteknev + ", keresztnev=" + keresztnev + ", email=" + email
				+ ", telefonszam=" + telefonszam + ", ceg=" + ceg.toString() + ", megjegyzes=" + megjegyzes + ", statusz="
				+ statusz + "]";
	}
	
}
