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
			flash.success("Successfully Logged in...");
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
	
	public static void edit(Long id) {
		checkApplicantSession();
		Applicant applicant = connectApplicant();
		render(applicant);
	}
	
	public static void update(Long id, @Valid Applicant applicant) {
		checkApplicantSession();
		Applicant newApplicant = Applicant.findById(id);
		newApplicant.firstName = applicant.firstName;
		newApplicant.middleName = applicant.middleName;
		newApplicant.lastName = applicant.lastName;
		newApplicant.phone = applicant.phone;
		newApplicant.qualification = applicant.qualification;
		newApplicant.expertise = applicant.expertise;
		newApplicant.yearsOfExperience = applicant.yearsOfExperience;
		newApplicant.address = applicant.address;
		newApplicant.validateAndSave();
		flash.success("Profile updated...");
		index();
	}
	
	// Change the Password View...
	public static void changePassword() {
		checkApplicantSession();
		Applicant applicant = connectApplicant();
		render(applicant);
	}
	
	public static void updatePassword(String oldPassword, 
		String newPassword, String verifyPassword) {
		checkApplicantSession();
		validation.required(oldPassword);
		validation.required(verifyPassword);
		validation.required(newPassword);

		Applicant applicant = Applicant.findById(connectApplicant().id);
		
		if(!validation.hasErrors()) {
			if(checkOldPassword(applicant, oldPassword)) {
				if(newPassword.equals(verifyPassword)) {
					applicant.password = newPassword;
					applicant.save();
					flash.success("Password updated...");
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
	
	private static boolean checkOldPassword(Applicant applicant, String oldPassword) {
		if (applicant.password.equals(oldPassword)) 
			return true; 
		else 
			return false;
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