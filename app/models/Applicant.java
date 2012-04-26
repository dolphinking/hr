package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Applicant extends Model {
	
	@Required
	public String firstName;
	
	public String middleName;
	
	@Required
	public String lastName;
	
	@Required
	@Column(unique=true)
	public String email;
	
	@Required
	public String password;
		
	public String phone;
	
	public String qualification;
	
	public String expertise;
	
	public String yearsOfExperience;
	
	@MaxSize(10000)
	@Lob
	public String address;
	
	public Blob cv;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public Set<Job> jobs;
	
	public Applicant(String firstName, String middleName, String lastName, String password, 
	String email, String phone, String qualification, String expertise,
	String yearsOfExperience, String address, Blob cv) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.qualification = qualification;
		this.expertise = expertise;
		this.yearsOfExperience = yearsOfExperience;
		this.address = address;
		this.cv = cv;
		this.jobs = new TreeSet<Job>();
	}
}