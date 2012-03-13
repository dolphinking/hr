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
	public Double experience;
	
	@Required
	public Double salary;
	
	@MaxSize(1000)
	public String benefits;
	
	@Required
	public Date postedDate;
	
	@Required
	public Date expiredDate;
	
	@ManyToOne
	@Required
	public JobCategory category;
	
	@ManyToOne
	@Required
	public Employee employee;
	
	public Job(String title, String description, String gender, String qualification, 
	Double experience, Double salary, String benefits, Date postedDate, Date expiredDate,
	JobCategory category, Employee employee) {
		this.title = title;
		this.description = description;
		this.gender = gender;
		this.qualification = qualification;
		this.experience = experience;
		this.salary = salary;
		this.benefits = benefits;
		this.postedDate = postedDate;
		this.expiredDate = expiredDate;
		this.category = category;
		this.employee = employee;
	}
}