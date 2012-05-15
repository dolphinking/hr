package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Job extends Model {
	
	@Required
	public String title;
	
	@Required
	@MaxSize(100000)
	@Lob
	public String description;
	
	@Required
  public String gender;
	
	public String qualification;
	
	public String experience;
	
	public String salary;
	
	public String benefits;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date postedDate;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date expiryDate;
	
	@Required
	@ManyToOne
	public JobCategory category;
	
	@Required
	@ManyToOne
	public Employee employee;
	
	public Job(String title, String description, String gender, String qualification, 
	String experience, String salary, String benefits, Date postedDate, Date expiryDate,
	JobCategory category, Employee employee) {
		this.title = title;
		this.description = description;
		this.gender = gender;
		this.qualification = qualification;
		this.experience = experience;
		this.salary = salary;
		this.benefits = benefits;
		this.postedDate = postedDate;
		this.expiryDate = expiryDate;
		this.category = category;
		this.employee = employee;
	}
	
	public static List<Job> getListOfJobsAccordingToCategory(Long id) {
		List<Job> jobs = Job.find("Select count(j) from Job j, JobCategory jc where j.category=jc.id and j.category.id=?",id).fetch();
		return jobs;
	}
}