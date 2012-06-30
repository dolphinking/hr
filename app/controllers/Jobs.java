package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import models.*;

public class Jobs extends Controller {
	
	// Before filter checkEmployee, checks the currently logged in employee's sessions
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
		if(jobCategories.size() != 0) {
			render(jobCategories, employee);
		} else {
			flash.error("Please add some job categories before posting new jobs...");
			postJob();
		}
	}
	
	// Post new jobs after filling up form...
	// @param job is the job object...
	public static void postNewJob(@Valid Job job) {
		// Lots of other validations are need here
		validation.required(job.category);
		validation.required(job.title);
		validation.required(job.description);
		validation.required(job.gender);
		validation.required(job.postedDate);
		validation.required(job.expiryDate);
		validation.required(job.employee);
		
		if(validation.hasErrors()) {
			flash.error("Something is wrong...while posting new Job");
			newJob();
		} else {
			if(job != null) {
				if(job.expiryDate.before(job.postedDate) || job.expiryDate.before(new Date())) {
					flash.error("Invalid Expiry Date.");
					newJob();
				}
				job.status = true;
				job.create();
				flash.success("New Job added successfully.");
				postJob();
			}
		}
	}
	
	// Shows the individual job...
	// @param id is the job id with long data type...
	public static void showJob(Long id) {
		Job job = Job.findById(id);
		render(job);
	}
	
	// Edit page for the Job...
	// @param id is the job id with long data type...
	public static void editJob(Long id) {
		List<JobCategory> jobCategories = JobCategory.findAll();
		Employee employee = Employees.connected();
		Job job = Job.findById(id);
		render(jobCategories, employee, job);
	}
	
	// Update the particular job...
	// @param id and job are the job id and job object...
	public static void updateJob(Long id, @Valid Job job) {
		Job updatablejob = Job.findById(id);
		updatablejob.category = job.category;
		updatablejob.title = job.title;
		updatablejob.description = job.description;
		updatablejob.gender = job.gender;
		updatablejob.qualification = job.qualification;
		updatablejob.experience = job.experience;
		updatablejob.salary = job.salary;
		updatablejob.benefits = job.benefits;
		updatablejob.postedDate = job.postedDate;
		updatablejob.expiryDate = job.expiryDate;
		updatablejob.employee = job.employee;
		if(updatablejob != null) {
			if(job.expiryDate.before(job.postedDate)) {
				flash.error("Invalid Expiry Date.");
				editJob(id);
			}
			if(job.expiryDate.before(new Date())) {
				updatablejob.status = false;
			} else {
				updatablejob.status = true;
			}
			updatablejob.validateAndSave();
			flash.success("Successfully updated.");
		}
		postJob();
	}	
	
	// Removes the job object...
	// @param id is the job id...
	public static void removeJob(Long id) {
		Job job = Job.findById(id);
		if(job != null) {
			job.delete();
			flash.success("Successfully deleted.");
		}
		postJob();
	}
	
	// Shows the list of applicants if they have applied for specific jobs...
	// @param id is the Job id to catch the applicant...
	public static void listOfApplicants(Long id) {
		Job job = Job.findById(id);
		List<Applicant> applicants = Applicant.find("select a from Applicant a join a.jobs as j where j.id=?", id).fetch();
		if(job != null && applicants.size() > 0)
			render(job, applicants);
		else
			postJob();
	}
}