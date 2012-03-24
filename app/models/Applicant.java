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
	public String username;
	
	@Required
	public String password;
	
	@Required
	@Column(unique=true)
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
	@MaxSize(10000)
	@Lob
	public String address;
	
	@Required
	public Blob cv;
	
	@Required
	public Blob coverLetter;
	
	@ManyToMany(cascade=CascadeType.ALL)
    public Set<Job> jobs;
	
	public Applicant(String firstName, String middleName, String lastName, String username,
	String password, String email, String phone, String qualification, String expertise,
	Double yearsOfExperience, String address) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.qualification = qualification;
		this.expertise = expertise;
		this.yearsOfExperience = yearsOfExperience;
		this.address = address;
		this.jobs = new TreeSet<Job>();
	}
	
	public Applicant jobItWith(String title) {
        jobs.add(Job.findOrCreateByTitle(title));
        return this;
    }
    
    public static List<Applicant> findJobbedWith(String title) {
        return Applicant.find(
            "select distinct a from Applicant a join a.jobs as j where j.title = ?",
            title
        ).fetch();
    }
}