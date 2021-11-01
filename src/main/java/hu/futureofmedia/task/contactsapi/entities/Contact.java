package hu.futureofmedia.task.contactsapi.entities;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import hu.futureofmedia.task.contactsapi.others.StatuszType;

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
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=true)
	private String telefonszam;
	
	@ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	private Company ceg;
	
	@Column(nullable=false)
	private String megjegyzes;
	
	@JsonBackReference
	@Enumerated(EnumType.STRING)
	@Column( nullable=false)
	private StatuszType statusz = StatuszType.AKTIV;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column( nullable=false)
	private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column( nullable=true)
	private Date updated_at;
	
	public Contact() {}
	
	public Contact(
			String vezeteknev, 
			String keresztnev, 
			String email, 
			String telefonszam, 
			Company ceg,
			String megjegyzes, 
			StatuszType statusz) 
	{
		this.vezeteknev = vezeteknev;
		this.keresztnev = keresztnev;
		this.email = email;
		this.telefonszam = telefonszam;
		this.ceg = ceg;
		this.megjegyzes = megjegyzes;
		this.statusz = statusz;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public StatuszType getStatusz() {
		return statusz;
	}

	public void setStatusz(StatuszType statusz) {
		this.statusz = statusz;
	}
	
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	public boolean EmailValidation() {
		Pattern ValidEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = ValidEmailRegex.matcher(email);
		
		return matcher.find();
	}
	
	public boolean TelefonszamValidation() {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		return util.isPossibleNumber(telefonszam, "E-164");
	}

	@Override
	public String toString() {
		return "Contact [vezeteknev=" + vezeteknev + ", keresztnev=" + keresztnev + ", email=" + email
				+ ", telefonszam=" + telefonszam + ", ceg=" + ceg.toString() + ", megjegyzes=" + megjegyzes + ", statusz="
				+ statusz + "]";
	}
	
}
