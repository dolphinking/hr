package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class DepartmentController extends Controller {
	
	@Before
    static void checkEmployee() {
       EmployeeController.checkSession();
    }
	
	public static void index() {
		List<Department> departments = Department.findAll();
		render(departments);
	}
	
	public static void addDepartment(String department) {
		validation.required(department);
		if(validation.hasErrors()) {
	        flash.error("Please enter the valid department...");
        } else {
			new Department(department).save();
		}
		index();
	}
	
	public static void removeDepartment(Long id) {
		Department department = Department.findById(id);
		department.delete();
		index();
	}
    
}

