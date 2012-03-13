import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
		
	/*
	*
	* Test for creating and retrieving Job, Employee and Applicant from the database.
	*/
	@Test
	public void createAndRetrieveEmployeeAndJob() {
		
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
		Job job = new Job("Sales Girl", "Beautiful", "Female", "Bachelors", 1.5, 1000.00,
		"Nothing", new Date(), new Date(), category, employee).save();
				
		// Created the instance of Employee and searched for first data.
		Job newJob = Job.find("byDescription", "Beautiful").first();
		
		//Test
		assertNotNull(job);
		assertEquals("Beautiful", newJob.description);
		
		// Create an instance of Applicant
		Applicant applicant = new Applicant("Ram", "Kumar", "Lama", "ram", "ram12345","ram@gmail.com", "9849038521", "Bachelors", "Java, PHP, C++", 2.5, "Kathmandu", job).save();
		      				
		// Created an instance of Applicant and searched for first data.
		Applicant newApplicant = Applicant.find("byUsername", "ram").first();
		
		//Test
		assertNotNull(newApplicant);
		assertEquals("ram", newApplicant.userName);
	}
}