package controllers;

import java.util.*;

import play.*;
import play.mvc.*;
import play.libs.*;
import play.cache.*;
import play.data.validation.*;

import models.*;
import notifiers.*;

public class Applicants extends Controller {
	
	@Before
  static void addApplicant() {
		Applicant applicant = connectApplicant();
    if(applicant != null) {
			renderArgs.put("userApplicant", applicant);
		}
  }
  
  static Applicant connectApplicant() {
    if(renderArgs.get("userApplicant") != null) {
			return renderArgs.get("userApplicant", Applicant.class);
		}
		String email = session.get("userApplicant");
		if(email != null) {
			return Applicant.find("byEmail", email).first();
		}
		return null;
  }

	// Checks Session...
  static void checkApplicantSession() {
		if(connectApplicant() == null) {
			flash.error("Please log in first.");
			login();
		}
  }
	
	// @username is the username variable to capture the value of username
	// @password is the password variable to capture the value of password
	public static void checkApplicant(String email, String password) {
		Applicant applicant = Applicant.find("byEmailAndPassword", email, password).first();
		if(applicant != null) {
			session.put("userApplicant", applicant.email);
			index();
		}
		// if the username or password is invalid....
		flash.put("email", email);
		flash.error("Invalid username or password...");
		login();
	}
	
	public static void login() {
		render();
	}
	
	public static void index() {
		checkApplicantSession();
		Applicant applicant = connectApplicant();
		render(applicant);
	}
	
	public static void logout() {
		session.clear();
		login();
	}
	
	public static void signup() {
		String randomID = Codec.UUID();
		render(randomID);
	}
    
	public static void save(@Valid Applicant applicant, 
		String verifyPassword, String code, String randomID) {
		
		validation.required(applicant.firstName);
		validation.required(applicant.lastName);
		validation.required(applicant.email);
		validation.required(applicant.password);
		validation.required(verifyPassword);
	  validation.equals(verifyPassword, applicant.password).message("Your password doesn't match");
		validation.required(code).message("Please type the code");
	
		if(!Play.id.equals("test")) {
			validation.equals(code, Cache.get(randomID)).message("Invalid code. Please type it again");
		}
		
		if(validation.hasErrors()) {
			render("@signup", applicant, verifyPassword, randomID);
		}
		applicant.create();
		Cache.delete(randomID);
		Mails.welcome(applicant);
		login();
	}
	
	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#EEE");
		Cache.set(id, code, "30mn");
		renderBinary(captcha);
	}

}