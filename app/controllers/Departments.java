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
	        flash.error("Please enter the valid department...");
        } else {
			department.create();
		}
		index();
	}
	
	public static void removeDepartment(Long id) {
		Department department = Department.findById(id);
		department.delete();
		index();
	}
    
}

