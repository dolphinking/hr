package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class EmployeeController extends Controller {
	
	public static void index() {
		if(session.get("userEmployee") != null) {
			ApplicationController.index();
		}
		render();
	}
	
	public static void login(String username, String password) {
		Employee employee = Employee.find("byUsernameAndPassword", username, password).first();
		if(employee != null) {
			session.put("userEmployee", employee.username);
            flash.success("Welcome, " + employee.fullName);
            ApplicationController.index();         
        }
        // Oops
        flash.put("username", username);
        flash.error("Invalid username or password...");
        index();
	}
	
	public static void logout() {
		session.clear();
		ApplicationController.index();
	}
}