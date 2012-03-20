package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Employees extends Controller {
	
	@Before
    static void addEmployee() {
        Employee employee = connected();
        if(employee != null) {
            renderArgs.put("userEmployee", employee);
        }
    }
    
    static Employee connected() {
        if(renderArgs.get("userEmployee") != null) {
            return renderArgs.get("userEmployee", Employee.class);
        }
        String username = session.get("userEmployee");
        if(username != null) {
            return Employee.find("byUsername", username).first();
        } 
        return null;
    }

    static void checkSession() {
        if(connected() == null) {
            flash.error("Please log in first");
            login();
        }
    }

	// Index Action Page...
	public static void index() {
		checkSession();
		render();
	}
	
	// Login Action Page...
	public static void login() {
        render();
	}
	
	// @username is the username variable to capture the value of username
	// @password is the password variable to capture the value of password
	public static void checkEmployee(String username, String password) {
		Employee employee = Employee.find("byUsernameAndPassword", username, password).first();
		if(employee != null) {
			session.put("userEmployee", employee.username);
            // flash.success("Welcome, " + employee.fullName);
            index();         
        }
        // if the username or password is invalid....
        flash.put("username", username);
        flash.error("Invalid username or password...");
		login();
	}
	
	// Logout Action Page....
	public static void logout() {
		session.clear();
		login();
	}
	
	// List of CV store in this POOL....
	public static void cvPool() {
		checkSession();
		render();
	}
	
	// Settings of currrently logged in user....
	public static void settings() {
		checkSession();
		Employee employee = connected();
		List<Department> departments = Department.findAll();
		List<Role> roles = Role.findAll();
		render(employee, departments, roles);
	}
	
	// Update the change of the logged in user....
	public static void updateUser(Long id, @Valid Employee employee) {
		checkSession();
		Employee updatedEmployee = Employee.findById(id);
		updatedEmployee.fullName = employee.fullName;
		updatedEmployee.email = employee.email;
		updatedEmployee.phone = employee.phone;
		updatedEmployee.department = employee.department;
		updatedEmployee.role = employee.role;
		updatedEmployee.validateAndSave();
		settings();
	}
	
	// List all the employees
	public static void listEmployees() {
		List<Employee> employees = Employee.findAll();
		render(employees);
	}
	
	// Change the Password View
	public static void changePassword() {
		checkSession();
		Employee employee = connected();
		render(employee);
	}
	
	// Action for changing actually password
	public static void alterPassword(String newPassword, String verifyPassword) {
		checkSession();
		validation.required(verifyPassword);
		validation.required(newPassword);
        validation.equals(verifyPassword, newPassword).message("Your password doesn't match");
		Employee newEmployee = Employee.findById(connected().id);
		newEmployee.password = newPassword;
		
		if(validation.hasErrors()) {
			render("@changePassword");
        }
		newEmployee.save();
		changePassword();
	}
}