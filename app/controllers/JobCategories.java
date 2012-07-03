package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.validation.*;
import play.modules.paginate.ValuePaginator;

import models.*;

public class JobCategories extends Controller {
	
	
	// Before Filter checkEmployee, checks the employee session...
	@Before
	static void checkEmployee() {
		Employees.checkSession();
	}
	
	// Home page for job categories...
	public static void index() {
		List<JobCategory> jobCategories = JobCategory.findAll();
		ValuePaginator paginator = new ValuePaginator(jobCategories);
		paginator.setPageSize(4);
		render(paginator);
	}
	
	// Saves the new Job category...
	// @params category is JobCategory object...
	public static void addJobCategory(@Valid JobCategory category) {
		validation.required(category.name);
		if(validation.hasErrors()) {
			flash.error("Please enter the valid job category...");
		} else {
			category.create();
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