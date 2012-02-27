package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Employee extends Model {
	
	@Required
	public String fullName;
	
	@Required
	public String userName;
	
	@Required
	public String password;
	
	@Required
	public String email;
	
	@Required
	public String phone;
	
	@ManyToOne
	@Required
	public Department department;
	
	@ManyToOne
	@Required
	public Role role;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public Set<Expertise> followedExpertise = new HashSet<Expertise>();
	
	public Employee(String fullName, String userName, String password, String email, String phone, Department department, Role role) {
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.department = department;
		this.role = role;
	}
	
}

