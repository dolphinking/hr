package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.validation.*;
import play.modules.paginate.ValuePaginator;

import models.*;

public class Departments extends Controller {
	
	// Before filter checkEmployee checks the session for the current employee...
	@Before
	static void checkEmployee() {
		Employees.checkSession();
	}
	
	// Home page for department model...
	public static void index() {
		List<Department> departments = Department.findAll();
		ValuePaginator paginator = new ValuePaginator(departments);
		paginator.setPageSize(4);
		render(paginator);
	}
	
	// Saves the new department...
	// @param department is the department object...
	public static void addDepartment(@Valid Department department) {
		validation.required(department.name);
		if(validation.hasErrors()) {
			flash.error("Please enter the valid department.");
		} else {
			try {
				department.create();
			} catch(Exception e) {
				flash.error(e.getMessage());
			}
		}
		index();
	}
	
	// Edit Department with one param...
	// @param id is the department Id...
	public static void editDepartment(Long id) {
		Department department = Department.findById(id);
		render(department);
	}
	
	// Update action for department object...
	// @param id and department are long datatype and department object respectively...
	public static void updateDepartment(Long id, @Valid Department department) {
		validation.required(department.name);
		
		Department updateableDepartment = Department.findById(id);
		updateableDepartment.name = department.name;
		
		if(validation.hasErrors()) {
			flash.error("Please enter the valid department.");
			editDepartment(updateableDepartment.id);
		} else {
			updateableDepartment.validateAndSave();
			flash.success("Successfully updated.");
			index();
		}
	}
}