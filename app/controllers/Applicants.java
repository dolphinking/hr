package controllers;

import java.io.*;
import java.util.*;

import play.*;
import play.mvc.*;
import play.libs.*;
import play.cache.*;
import play.db.jpa.*;
import play.data.validation.*;
import play.modules.paginate.ValuePaginator;

import models.*;
import notifiers.*;

public class Applicants extends Controller {
	
	
	// Before filter addApplicant, which checks the user sessions...
	@Before
  static void addApplicant() {
		Applicant applicant = connectApplicant();
    if(applicant != null) {
			renderArgs.put("userApplicant", applicant);
		}
  }
  
	// connectApplicant is the Current Applicant logged in...
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
	
	// Checks the correct Applicant for login...
	// @params username and password is the username and password for applicant...
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
	
	// Applicant Login Form...
	public static void login() {
		render();
	}
	
	// Applicant Home Page...
	public static void index() {
		checkApplicantSession();
		Applicant applicant = connectApplicant();
		render(applicant);
	}
	
	// Forgot Password Page...
	public static void forgotPassword() {
		render();
	}
	
	// sendPassword is the action for sending the old password...
	// @param email for sending the password if the applicant is registered...
	public static void sendPassword(String email) {
		validation.required(email);
		Applicant applicant = Applicant.find("byEmail",email).first();
		if(applicant != null) {
			Mails.lostPassword(applicant);
			flash.success("Check your email to get the password...");
			login();
		} else {
			flash.error("Applicant invalid...");
			forgotPassword();
		}
	}
	
	// Editing Page for logged in Applicant
	// @param id is the applicant id.
	public static void edit(Long id) {
		checkApplicantSession();
		Applicant applicant = connectApplicant();
		render(applicant);
	}
	
	// Some changes should be there for file upload...
	// @param id is the applicant id for update.
	// @param applicant is the applicant for update.
	// @param file is the cv file to upload.
	public static void update(Long id, @Valid Applicant applicant, File file)
		throws FileNotFoundException {
		checkApplicantSession();
		
		if(file != null) {
			Applicant newApplicant = Applicant.findById(id);
			newApplicant.fullName = applicant.fullName;
			newApplicant.phone = applicant.phone;
			newApplicant.qualification = applicant.qualification;
			newApplicant.expertise = applicant.expertise;
			newApplicant.yearsOfExperience = applicant.yearsOfExperience;
			newApplicant.address = applicant.address;
			newApplicant.fileName = file.getName();
			newApplicant.file = new Blob();
			newApplicant.file.set(new FileInputStream(file), MimeTypes.getContentType(file.getName()));
			
			if(newApplicant != null) {
				newApplicant.validateAndSave();
				flash.success("Profile updated...");
			} else {
				flash.error("Please check all the values...");
			}
		} else {
			flash.error("Please include your CV to proceed further...");
			index();
		}
		index();
	}
	
	
	// Download the specific CV of connected Applicant...
	// @param id is the applicant id. 
	public static void downloadFile(Long id) {
    final Applicant doc = Applicant.findById(id);
    notFoundIfNull(doc);
    response.setContentTypeIfNotSet(doc.file.type());
    renderBinary(doc.file.get(), doc.fileName);
  }
	
	// Change the Password View...
	public static void changePassword() {
		checkApplicantSession();
		Applicant applicant = connectApplicant();
		render(applicant);
	}
	
	// Update the password
	// @param oldPassword is old password
	// @param newPassword is new password
	// @param verifyPassword is to verify password
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
	
	// This checks the old password whether the password is correct or not.
	// @params applicant is the given applicant object.
	// @params oldPassword is the old password of given applicant object.
	private static boolean checkOldPassword(Applicant applicant, String oldPassword) {
		if (applicant.password.equals(oldPassword)) 
			return true; 
		else 
			return false;
	}
	
	// This destory the current session and goes to the login form.
	public static void logout() {
		session.clear();
		login();
	}
	
	// This is the signup form for the new applicant.
	public static void signup() {
		String randomID = Codec.UUID();
		render(randomID);
	}
  
	// This actually saves the new Applicant in database.
	// @param applicant is the new applicant object.
	// @param verifyPassword is to verify two password.
	// @params code and randomID is the code for captcha.
	public static void save(@Valid Applicant applicant, 
		String verifyPassword, String code, String randomID) {
		
		validation.required(applicant.fullName);
		validation.required(applicant.email);
		validation.required(applicant.password);
		validation.required(verifyPassword);
		validation.required(applicant.expertise);
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
	
	// This is the captcha page.
	// @param id is the id of the captcha id.
	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#EEE");
		Cache.set(id, code, "30mn");
		renderBinary(captcha);
	}
	
	// Shows the list of currently available jobs
	public static void listOfJobs() {
		checkApplicantSession();
		List<JobCategory> jobCategories = JobCategory.findAll(); 
		render(jobCategories);
	}
	
	// Shows the list of jobs according to job category.
	// @param id is the jobcategory id.
	public static void jobShow(Long id) {
		checkApplicantSession();
		List<Job> jobs = Job.find("select j from Job j join j.category as c where j.status='TRUE' and c.id = ?", id).fetch();
		JobCategory jobCategory = JobCategory.findById(id);
		render(jobs, jobCategory);
	}
	
	// This show the description of the current job.
	// @param id is the job id.
	public static void jobDescription(Long id) {
		checkApplicantSession();
		Job job = Job.findById(id);
		render(job);
	}
	
	// The method should insert the data into Applicant_Job Table
	// @params id is job id of current session applicant's id should be inserted.
	// This data should be shown in the index page of Applicant
	public static void applyJob(Long id) {
		checkApplicantSession();
		Job job = Job.findById(id);
		Applicant applicant = connectApplicant();
		if(job != null) {
			if(checkApplicantAndJob(job, applicant)) {
				applicant.jobItWith(job.id).save();
				flash.success("You have successfully applied for " + job.title);
			} else {
				flash.error("You have already applied for this job");
			}
		} else {
			flash.error("You are applying for null job.");
		}
		index();
	}
	
	// This method checks whether the applicant is applying for same job again and again.
	// @param job is the Job Object
	// @param applicant is the Applicant Object.
	private static boolean checkApplicantAndJob(Job job, Applicant applicant) {
		if(job != null && applicant != null) {
			List<Applicant> applicants = applicant.findJobWithApplicant(job.id, applicant.id);
			if(applicants.size() > 0) {
				for(int i=0; i<applicants.size(); i++) {
					System.out.println(applicants.get(i).email);
					if(applicants.get(i).email.equals(connectApplicant().email)) {
						return false;
					} else {
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;	
	}
	
	// Shows the list of Jobs applied by current or logged in applicant
	public static void jobApplied() {
		checkApplicantSession();
		List<Applicant> listOfJobs = connectApplicant().findAllJobByApplicant(connectApplicant());
		ValuePaginator paginator = new ValuePaginator(listOfJobs);
		paginator.setPageSize(5);
		render(paginator);
	}
}