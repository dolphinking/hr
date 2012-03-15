package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class EmployeeController extends Controller {
	
	public static void index() {
		if(session.get("userEmployee") == null) {
			login();
		}
		render();
	}
	
	public static void login() {
		render();
	}
	
	public static void checkEmployee(String username, String password) {
		Employee employee = Employee.find("byUsernameAndPassword", username, password).first();
		if(employee != null) {
			session.put("userEmployee", employee.username);
            flash.success("Welcome, " + employee.fullName);
            index();         
        }
        // Oops
        flash.put("username", username);
        flash.error("Invalid username or password...");
        index();
	}
	
	public static void logout() {
		session.clear();
		index();
	}
}