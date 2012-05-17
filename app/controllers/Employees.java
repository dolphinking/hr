package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import models.*;
import notifiers.*;

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
		String email = session.get("userEmployee");
		if(email != null) {
			return Employee.find("byEmail", email).first();
		} 
		return null;
	}
	
	// Checks Session...
	static void checkSession() {
		if(connected() == null) {
			flash.error("Please log in first");
			login();
		}
	}
	
	// Login Action Page...
	public static void login() {
		render();
	}
	
	// @username is the username variable to capture the value of username
	// @password is the password variable to capture the value of password
	public static void checkEmployee(String email, String password) {
		Employee employee = Employee.find("byEmailAndPassword", email, password).first();
		if(employee != null) {
			session.put("userEmployee", employee);
			// flash.success("Welcome, " + employee.fullName);
			cvPool(null);
		}
		// if the username or password is invalid....
		flash.put("email", email);
		flash.error("Invalid username or password...");
		login();
	}
	
	// Logout Action Page....
	public static void logout() {
		session.clear();
		login();
	}
	
	// List of CV store in this POOL....
	public static void cvPool(String expertise) {
		checkSession();
		List<Applicant> applicants = Applicant.findAll();

		if(expertise != null) {
			applicants = Applicant.find("byExpertiseIlike", expertise+"%").fetch();
		}
		render(applicants);
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
		updatedEmployee.phone = employee.phone;
		updatedEmployee.department = employee.department;
		updatedEmployee.role = employee.role;
		if(updatedEmployee != null) {
			updatedEmployee.validateAndSave();
			flash.success("Your profile is successfully updated.");
		} else {
			flash.error("Couldn't update your profile.");
		}
		settings();
	}
	
	// List all the employees
	public static void listEmployees() {
		checkSession();
		List<Employee> employees = Employee.find("select e from Employee e where e.id!=?", connected().id).fetch();
		render(employees);
	}
	
	// Action View for new Employee...
	public static void newEmployee() {
		checkSession();
		List<Department> departments = Department.findAll();
		List<Role> roles = Role.findAll();
		render(departments, roles);
	}
	
	// Save the new Employee...
	public static void saveEmployee(@Valid Employee employee, String verifyPassword) {
		checkSession();
		// Lots of other validations are need here
		validation.required(employee.fullName);
		validation.required(employee.email);
		validation.required(employee.password);
		validation.required(employee.phone);
		validation.required(employee.department);
		validation.required(employee.role);
		validation.equals(verifyPassword, employee.password).message("Your password doesn't match");
		
		if(validation.hasErrors()) {
			flash.error("Somethings is wrong...while adding new employee");
			newEmployee();
		} else {
			employee.create();
			listEmployees();
		}
	}
	
	// Remove the Employee...
	public static void removeEmployee(Long id) {
		checkSession();
		Employee employee = Employee.findById(id);
		employee.delete();
		listEmployees();
	}
	
	// Edit the other Employee except the logged in user...
	public static void editEmployee(Long id) {
		checkSession();
		Employee employee = Employee.findById(id);
		List<Department> departments = Department.findAll();
		List<Role> roles = Role.findAll();
		render(employee, departments, roles);
	}
	
	// Update the contents of other employees
	public static void updateEmployee(Long id, @Valid Employee employee) {
		checkSession();
		validation.required(employee.fullName);
		validation.required(employee.phone);
		validation.required(employee.email);
		validation.required(employee.department);
		validation.required(employee.role);
		
		Employee updatedEmployee = Employee.findById(id);
		updatedEmployee.fullName = employee.fullName;
		updatedEmployee.email = employee.email;
		updatedEmployee.phone = employee.phone;
		updatedEmployee.department = employee.department;
		updatedEmployee.role = employee.role;
		
		if(validation.hasErrors()) {
			flash.error("Somethings is wrong...while adding new employee");
			editEmployee(updatedEmployee.id);
		} else {
			updatedEmployee.validateAndSave();
			listEmployees();
		}
	}
	
	// Change the Password View...
	public static void changePassword() {
		checkSession();
		Employee employee = connected();
		render(employee);
	}
	
	// Action for changing actually password
	public static void alterPassword(String oldPassword, String newPassword, String verifyPassword) {
		checkSession();
		validation.required(oldPassword);
		validation.required(verifyPassword);
		validation.required(newPassword);

		Employee employee = Employee.findById(connected().id);
		
		if(!validation.hasErrors()) {
			if(checkOldPassword(employee, oldPassword)) {
				if(newPassword.equals(verifyPassword)) {
					employee.password = newPassword;
					employee.save();
					flash.success("Your new password is successfully changed.");
					changePassword();
				} else {
					flash.error("Error: your new password and verify password didn't match.");
				}
			} else {
				flash.error("Error: Your old password you have entered is wrong.");		
			}
		} else {
			flash.error("Error: Please fill all the fields.");
		}
		changePassword();
	}
	
	private static boolean checkOldPassword(Employee employee, String oldPassword) {
		if (employee.password.equals(oldPassword)) 
			return true; 
		else 
			return false;
	}
	
	public static void forgotPassword() {
		render();
	}
	
	public static void sendPassword(String email) {
		validation.required(email);
		Employee employee = Employee.find("byEmail",email).first();
		if(employee != null) {
			Mails.lostEmployeePassword(employee);
			flash.success("Check your email to get your password.");
			login();
		} else {
			flash.error("Invalid Employee.");
			forgotPassword();
		}
	}
}