package models;

import java.util.*;
import javax.persistence.*;

import play.data.binding.*;
import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class Employee extends Model {
	
	@Required
	public String fullName;
	
	@Required
	@Email
	public String email;
	
	@Required
    @MaxSize(15)
    @MinSize(5)
	public String password;
		
	@Required
	public String phone;
	
	@ManyToOne
	@Required
	public Department department;
	
	@ManyToOne
	@Required
	public Role role;
	
	public Employee(String fullName, String password, String email, 
	String phone, Department department, Role role) {
		this.fullName = fullName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.department = department;
		this.role = role;
	}
	
	public String toString() {
		return email;
	}
}