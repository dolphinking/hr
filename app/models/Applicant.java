package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;
import play.modules.search.*;

@Entity
@Indexed
public class Applicant extends Model {
	
	@Required
	public String fullName;
		
	@Required
	@Column(unique=true)
	public String email;
	
	@Required
	public String password;
		
	public String phone;
	
	public String qualification;
	
	@Required
	@Field
	public String expertise;
	
	public String yearsOfExperience;
	
	public String address;
	
	public String fileName;
	
	public Blob file;
	
	@ManyToMany
	public List<Job> jobs;
	
	public Applicant(String fullName, String password, String email, String phone, 
	String qualification, String expertise, String yearsOfExperience, String address) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.qualification = qualification;
		this.expertise = expertise;
		this.yearsOfExperience = yearsOfExperience;
		this.address = address;
		this.jobs = new ArrayList<Job>();
	}
	
	@Override
	public void _delete() {
	   super._delete();
	   file.getFile().delete();
	}
	
	public Applicant jobItWith(Long id) {
		jobs.add(Job.findJobById(id));
		return this;
	}
	
	public static List<Applicant> findJobbedWith(String title) {
		return Applicant.find("select distinct a from Applicant a join a.jobs as j where j.title = ?",title).fetch();
	}
	
	public static List<Applicant> findAllJobByApplicant(Applicant applicant) {
		return Applicant.find("select j from Job j join j.applicant as a where a.id = ?", applicant.id).fetch();
	}
	
	public static List<Applicant> findJobWithApplicant(Long jobId, Long appId) {
		return Applicant.find("select a from Applicant a join a.jobs as j where j.id=? and a.id=?", jobId, appId).fetch();
	}
}