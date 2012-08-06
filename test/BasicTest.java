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
		
		// Created new instance of employee to save the sample data.
		Employee employee = new Employee("Suraj Tamang", "suraj123", "surajyonzon@gmail.com", "9849038521", department).save();

		// Created the instance of Employee and searched for first data.
		Employee emp = Employee.find("byEmail", "surajyonzon@gmail.com").first();
		
		//Test
		assertNotNull(emp);
		assertEquals("surajyonzon@gmail.com", employee.email);
		
		// Create the job category table at first.
		JobCategory category = new JobCategory("Sales and Marketing").save();
				
		// Create the job table at first.
		Job job = new Job("Sales Girl", "Beautiful", "Female", "Bachelors", "1.5+ Above", "1000.00", "Nothing", true, new Date(), new Date(), category, employee).save();
		Job job2 = new Job("Sales Boy", "Handsome", "Male", "Bachelors", "1.5+ Above", "1000.00", "Nothing", true, new Date(), new Date(), category, employee).save();
		
		// Created the instance of Employee and searched for first data.
		Job newJob = Job.find("byGender", "Male").first();
		
		//Test
		assertNotNull(job);
		assertEquals("Male", newJob.gender);
		
		// Create an instance of Applicant
		Applicant applicant = new Applicant("Ram Kumar", "ram12345", "ram@gmail.com", "9849038521", "Bachelors", "Java, PHP, C++", "2.5", "Kathmandu").save();
					
		// Created an instance of Applicant and searched for first data.
		Applicant newApplicant = Applicant.find("byEmail", "ram@gmail.com").first();
		
		applicant.jobItWith(job.id).jobItWith(job2.id).save();
		
		//Check the many-to-many relations
		assertEquals(1, Applicant.findJobbedWith("Sales Girl").size());
		assertEquals(1, Applicant.findJobbedWith("Sales Boy").size());
		
		//Test
		assertNotNull(newApplicant);
		assertEquals("ram@gmail.com", newApplicant.email);
	}
}