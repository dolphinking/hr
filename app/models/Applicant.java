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
	public String userName;
	
	@Required
	public String password;
	
	@Required
	public String email;
	
	@Required
	public String phone;
	
	@Required
	public String qualification;
	
	@Required
	public String expertise;
	
	@Required
	public Double yearsOfExperience;
	
	@Required
	@Lob
	@MaxSize(5000)
	public String address;
	
	@Required
	public Blob cv;
	
	@Required
	public Blob coverLetter;
	
	@ManyToOne
	@Required
	public Job job;
	
	public Applicant(String firstName, String middleName, String lastName, String userName,
	String password, String email, String phone, String qualification, String expertise,
	Double yearsOfExperience, String address, Job job) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.qualification = qualification;
		this.expertise = expertise;
		this.yearsOfExperience = yearsOfExperience;
		this.address = address;
		this.job = job;
	}
}