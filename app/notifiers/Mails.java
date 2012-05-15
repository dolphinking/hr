package notifiers;
 
import play.*;
import models.*;
import play.mvc.*;
import java.util.*;
 
public class Mails extends Mailer {

	public static void welcome(Applicant applicant) {
		setSubject("Welcome %s", applicant.fullName);
		addRecipient(applicant.email);
		setFrom("HumanEquationUAE <humanequationtest@gmail.com>");
		send(applicant);
	}

	public static void lostPassword(Applicant applicant) {
		String newpassword = applicant.password;
		setFrom("HumanEquationUAE <humanequationtest@gmail.com>");
		setSubject("Your password has been reset");
		addRecipient(applicant.email);
		send(applicant, newpassword);
	}

}