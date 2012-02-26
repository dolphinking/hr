package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

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
	public Float yearsOfExperience;
	
	@Required
	@Lob
	@MaxSize(10000)
	public String address;
	
	@Required
	public Blob cv;
	
	@Required
	public Blob coverLetter;
	
	public Applicant(String firstName, String middleName, String lastName, String userName,
	String password, String email, String phone, Float yearsOfExperience, String address,
	Blob cv, Blob coverLetter) {
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.yearsOfExperience = yearsOfExperience;
		this.address = this.address;
		this.cv = cv;
		this.coverLetter = coverLetter;
	}
	
	
	
}

