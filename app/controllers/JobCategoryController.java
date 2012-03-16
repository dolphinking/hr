package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class JobCategoryController extends Controller {
	
	@Before
    static void checkEmployee() {
		EmployeeController.checkSession();
    }
	
	public static void index() {
		List<JobCategory> jobCategories = JobCategory.findAll();
		render(jobCategories);
	}
	
	public static void addJobCategory(String jobCategory) {
		validation.required(jobCategory);
		if(validation.hasErrors()) {
	        flash.error("Please enter the valid Job Category...");
        } else {
			new JobCategory(jobCategory).save();
		}
		index();
	}
	
	public static void removeJobCategory(Long id) {
		JobCategory jobCategory = JobCategory.findById(id);
		jobCategory.delete();
		index();
	}
}