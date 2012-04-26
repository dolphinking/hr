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

	public static void signup() {
		String randomID = Codec.UUID();
		render(randomID);
	}
	
	public static void login() {
		render();
	}
	
	public static void checkUser() {
		
	}
    
	public static void save(@Valid Applicant applicant, String verifyPassword,
		String code, String randomID) {
		
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
		// Mails.welcome(applicant);
		login();
	}
	
	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#EEE");
		Cache.set(id, code, "30mn");
		renderBinary(captcha);
	}

}

