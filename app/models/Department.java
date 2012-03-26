package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Department extends Model {

	@Required
	// @Column(unique=true)
	public String name;
	
	public Department(String name) {
		this.name = name;
	}
	
	public String toString()  {
        return "Department(" + name + ")";
    }
}