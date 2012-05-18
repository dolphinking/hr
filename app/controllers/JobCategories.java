package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class JobCategories extends Controller {
	
	@Before
	static void checkEmployee() {
		Employees.checkSession();
	}
	
	public static void index() {
		List<JobCategory> jobCategories = JobCategory.findAll();
		render(jobCategories);
	}
	
	public static void addJobCategory(@Valid JobCategory category) {
		validation.required(category.name);
		if(validation.hasErrors()) {
			flash.error("Please enter the valid job category...");
		} else {
			category.create();
		}
		index();
	}
	
	// TODO -> Need to add some validation in
	public static void removeJobCategory(Long id) {
		JobCategory jobCategory = JobCategory.findById(id);
		if(jobCategory != null) {
			jobCategory.delete();
			flash.success("Successfully deleted.");
		}
		index();
	}
	
	// Edit Form for the JobCategory by sending one param
	// param @id -> Long Datatype
	public static void editJobCategory(Long id) {
		JobCategory jobCategory = JobCategory.findById(id);
		render(jobCategory);
	}
	
	// Update the JobCategory by sending two params
	// param @id -> Long Datatype
	// param @jobCategory -> JobCategory Datatype
	public static void updateJobCategory(Long id, @Valid JobCategory jobCategory) {
		validation.required(jobCategory.name);
		
		JobCategory updateableJobCategory = JobCategory.findById(id);
		updateableJobCategory.name = jobCategory.name;
		
		if(validation.hasErrors()) {
			flash.error("Please enter the valid Job Category.");
			editJobCategory(updateableJobCategory.id);
		} else {
			updateableJobCategory.validateAndSave();
			flash.success("Successfully updated.");
			index();
		}
	}
}