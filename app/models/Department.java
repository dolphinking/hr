package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Department extends Model {
	
	@Required
	public String name;
	
	public Department(String name) {
		this.name = name;
	}
	
}

