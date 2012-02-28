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
	@Lob
	@MaxSize(10000)
	public String description;
	
	@Required
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Male', 'Female')")
    public String gender;
	
	@Required
	public String qualification;
	
	@Required
	public Double experience;
	
	@Required
	public Double salary;
	
	@MaxSize(1000)
	public String benefits;
	
	@ManyToOne
	@Required
	public JobCategory category;
	
	public Job(String title, String description, String gender, String qualification, 
	Double experience, Double salary, String benefits, JobCategory category) {
		this.title = title;
		this.description = description;
		this.gender = gender;
		this.qualification = qualification;
		this.experience = experience;
		this.salary = salary;
		this.benefits = benefits;
		this.category = category;
	}
	
}

