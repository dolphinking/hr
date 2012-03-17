package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Job extends Model {
	
	@Required
	public String title;
		
	@Required
	@MaxSize(10000)
	public String description;
	
	@Required
    public String gender;
	
	@Required
	public String qualification;
	
	@Required
	public String experience;
	
	@Required
	public Double salary;
	
	@MaxSize(1000)
	public String benefits;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date postedDate;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date expiryDate;
	
	@ManyToOne
	@Required
	public JobCategory category;
	
	@ManyToOne
	@Required
	public Employee employee;
	
	public Job(String title, String description, String gender, String qualification, 
	String experience, Double salary, String benefits, Date postedDate, Date expiryDate,
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
}