package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class EmployeeController extends Controller {
	
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
	
	// Profile of the currently logged in user....
	public static void profile() {
		checkSession();
		render();
	}
	
	// Settings of currrently logged in user....
	public static void settings() {
		checkSession();
		render();
	}
}