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
	
    @ManyToMany
	public List<Expertise> expertise;
	
	public Employee(String fullName, String userName, String password, String email, String phone, Department department, Role role) {
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.department = department;
		this.role = role;
		this.expertise = new ArrayList<Expertise>();
	}
	
	public Employee expertiseWith(String name) {
        expertise.add(Expertise.findOrCreateByName(name));
        return this;
    }
    
    public static List<Employee> findExpertiseWith(String exp) {
		return Employee.find(
		"select distinct p from Employee p join p.expertise as t where t.name = ?",
		exp).fetch();
   }
}

