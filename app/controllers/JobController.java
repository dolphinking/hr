package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class JobController extends Controller {
	
	@Before
    static void checkEmployee() {
		EmployeeController.checkSession();
    }
	
	// List of jobs will be listed here....
	public static void postJob() {
		List<Job> jobs = Job.findAll();
		render(jobs);
	}
	
	// New Job Action Page....
	public static void newJob() {
		List<JobCategory> jobCategories = JobCategory.findAll();
		render(jobCategories);
	}
	
	public static void postNewJob(@Valid Job job) {
		// Lots of other validations are need here
		validation.required(job.title);
		validation.required(job.description);
		validation.required(job.gender);
		validation.required(job.qualification);
		validation.required(job.experience);
		validation.required(job.salary);
		validation.required(job.benefits);
		validation.required(job.postedDate);
		validation.required(job.expiryDate);
		
		if(validation.hasErrors()) {
			render("@newJob", job);
		}
		job.create();
		postJob();
	}
	
	public static void removeJob(Long id) {
		// Job job = Job.findById(id);
		// 		job.delete();
		// 		postJob();
	}
    
}

