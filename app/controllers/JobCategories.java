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
	
	public static void removeJobCategory(Long id) {
		JobCategory jobCategory = JobCategory.findById(id);
		jobCategory.delete();
		index();
	}
}