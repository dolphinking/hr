package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Departments extends Controller {
	
	@Before
	static void checkEmployee() {
		Employees.checkSession();
	}
	
	public static void index() {
		List<Department> departments = Department.findAll();
		render(departments);
	}
	
	public static void addDepartment(@Valid Department department) {
		validation.required(department.name);
		if(validation.hasErrors()) {
			flash.error("Please enter the valid department.");
		} else {
			department.create();
		}
		index();
	}
	
	public static void removeDepartment(Long id) {
		Department department = Department.findById(id);
		if(department != null) {
			department.delete();
			flash.success("Successfully deleted.");
		}
		index();
	}
	
	// Edit Department with one param
	// @param id -> Long Datatype 
	public static void editDepartment(Long id) {
		Department department = Department.findById(id);
		render(department);
	}
	
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