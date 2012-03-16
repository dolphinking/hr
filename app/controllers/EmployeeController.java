package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class EmployeeController extends Controller {
	
	// Index Action Page...
	public static void index() {
		if(session.get("userEmployee") == null) {
			login();
		}
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
            flash.success("Welcome, " + employee.fullName);
            index();         
        }
        // if the username or password is invalid....
        flash.put("username", username);
        flash.error("Invalid username or password...");
        index();
	}
	
	// Logout Action Page....
	public static void logout() {
		session.clear();
		index();
	}
	
	// List of jobs will be listed here....
	public static void postJob() {
		render();
	}
	
	// List of CV store in this POOL....
	public static void cvPool() {
		render();
	}
	
	// Profile of the currently logged in user....
	public static void profile() {
		render();
	}
	
	// Settings of currrently logged in user....
	public static void settings() {
		render();
	}
	
	// List Job Action Page...
	public static void listJobs() {
		render();
	}
	
	// New Job Action Page....
	public static void newJob() {
		render();
	}
	
}