package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Jobs extends Controller {
	
	@Before
    static void checkEmployee() {
		Employees.checkSession();
    }
	
	// List of jobs will be listed here....
	public static void postJob() {
		List<Job> jobs = Job.find("order by postedDate desc").fetch();
		render(jobs);
	}
	
	// New Job Action Page....
	public static void newJob() {
		List<JobCategory> jobCategories = JobCategory.findAll();
		Employee employee = Employees.connected();
		render(jobCategories, employee);
	}
	
	public static void postNewJob(@Valid Job job) {
		// Lots of other validations are need here
		validation.required(job.category);
		validation.required(job.title);
		validation.required(job.description);
		validation.required(job.gender);
		validation.required(job.qualification);
		validation.required(job.experience);
		validation.required(job.salary);
		validation.required(job.postedDate);
		validation.required(job.expiryDate);
		validation.required(job.employee);
		
		if(validation.hasErrors()) {
			flash.error("Somethings is wrong...while submitting jobs");
			newJob();
		} else {
			job.create();
			postJob();
		}
	}
	
	public static void showJob(Long id) {
		Job job = Job.findById(id);
		render(job);
	}
	
	public static void editJob(Long id) {
		List<JobCategory> jobCategories = JobCategory.findAll();
		Employee employee = Employees.connected();
		Job job = Job.findById(id);
		render(jobCategories, employee, job);
	}
	
	public static void updateJob(Long id, @Valid Job job) {
		Job updatablejob = Job.findById(id);
		updatablejob.category = job.category;
		updatablejob.title = job.title;
		updatablejob.description = job.description;
		updatablejob.gender = job.gender;
		updatablejob.qualification = job.qualification;
		updatablejob.experience = job.experience;
		updatablejob.salary = job.salary;
		updatablejob.postedDate = job.postedDate;
		updatablejob.expiryDate = job.expiryDate;
		updatablejob.employee = job.employee;
		updatablejob.validateAndSave();
		postJob();
	}	
	
	public static void removeJob(Long id) {
		Job job = Job.findById(id);
		job.delete();
		postJob();
	}
    
}

