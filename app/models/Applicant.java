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
	
	@ManyToMany
	public List<Expertise> expertise;
	
	@Required
	public Double yearsOfExperience;
	
	@Required
	@Lob
	@MaxSize(10000)
	public String address;
	
	@Required
	public Blob cv;
	
	@Required
	public Blob coverLetter;
	
	public Applicant(String firstName, String middleName, String lastName, String userName,
	String password, String email, String phone, String qualification, 
	Double yearsOfExperience, String address) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.qualification = qualification;
		this.expertise = new ArrayList<Expertise>();
		this.yearsOfExperience = yearsOfExperience;
		this.address = address;
	}
	
	public Applicant expertiseWith(String name) {
        expertise.add(Expertise.findOrCreateByName(name));
        return this;
    }
    
    public static List<Applicant> findExpertiseWith(String exp) {
		return Applicant.find(
		"select distinct p from Applicant p join p.expertise as t where t.name = ?",
		exp).fetch();
   }
}

