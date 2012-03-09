import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
		
	/*
	*
	* Test for creating and retrieving Job and Category from the database.
	*/
	@Test
	public void createAndRetrieveJob() {
		// Create the department table at first.
		Department department = new Department("Accounts").save();
		
		// Create the role table at first.
		Role role = new Role("Admin").save();
		
		// Created new instance of employee to save the sample data.
		Employee employee = new Employee("Suraj Tamang", "suraj", "suraj123", "surajyonzon@gmail.com", "9849038521", department, role).save();
        
		// Created the instance of Employee and searched for first data.
		Employee emp = Employee.find("byUserName", "suraj").first();
		
		//Test
		assertNotNull(emp);
		assertEquals("suraj", employee.userName);
		
		// Create the job category table at first.
		JobCategory category = new JobCategory("Sales and Marketing").save();
				
		// Create the job table at first.
		Job job = new Job("Sales Girl", "Sexy", "Female", "Bachelors", 1.5, 1000.00,
		"Nothing", category, employee).save();
				
		// Created the instance of Employee and searched for first data.
		Job newJob = Job.find("byDescription", "Sexy").first();
		
		//Test
		assertNotNull(job);
		assertEquals("Sexy", newJob.description);
	}
	
	/*
	* Test for creating and retrieving Applicant from the database.
	*/
	@Test
	public void createAndRetrieveApplicant() {
				
		// Create the expertise table at first.
		Expertise expertise = new Expertise("IT").save();
		Expertise expertise2 = new Expertise("HR").save();
		
		// Created new instance of applicant to save the sample data.
		Applicant applicant = new Applicant("Suraj", "", "Tamang", "username",
			"password", "email@mail.com", "9849038521", "Bachelors", 
			2.4, "Kathmandu").save();
        
        // Tag it now
        applicant.expertiseWith("IT").expertiseWith("HR").save();
		
		// Check
        assertEquals(1, Applicant.findExpertiseWith("IT").size());
        assertEquals(1, Applicant.findExpertiseWith("HR").size());
		
		// Created the instance of Employee and searched for first data.
		Applicant app = Applicant.find("byUserName", "username").first();
		
		//Test
		assertNotNull(app);
		assertEquals("username", app.userName);
	}
	
}
