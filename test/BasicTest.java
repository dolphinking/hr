import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {
	
	/*
	* Test for creating and retrieving Department from the database.
	*/
	@Test
	public void createAndRetrieveDepartment() {
		// Created new instance of department to save the sample data.
		new Department("Accounts").save();
		
		// Created the instance of department and searched for first data.
		Department department = Department.find("byName", "Accounts").first();
		
		//Test
		assertNotNull(department);
		assertEquals("Accounts", department.name);
	}

	/*
	* Test for creating and retrieving Role from the database.
	*/
	@Test
	public void createAndRetrieveRole() {
		// Created new instance of department to save the sample data.
		new Role("Admin").save();
		
		// Created the instance of department and searched for first data.
		Role role = Role.find("byName", "Admin").first();
		
		//Test
		assertNotNull(role);
		assertEquals("Admin", role.name);
	}
	
	/*
	* Test for creating and retrieving Employee from the database.
	*/
	@Test
	public void createAndRetrieveEmployee() {
		// Create the department table at first.
		Department department = new Department("Accounts").save();
		
		// Create the role table at first.
		Role role = new Role("Admin").save();
		
		// Create the expertise table at first.
		Expertise expertise = new Expertise("IT","Information Technology",).save();
		
		// Created new instance of employee to save the sample data.
		new Employee("Suraj Tamang", "suraj", "suraj123", "surajyonzon@gmail.com", "9849038521", department, role).save();
		
		// Created the instance of Employee and searched for first data.
		Employee employee = Employee.find("byUserName", "suraj").first();
		
		//Test
		assertNotNull(employee);
		assertEquals("suraj", employee.userName);
	}
	
	

}
