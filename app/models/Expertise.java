package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Expertise extends Model {
	
	@Required
	public String name;
	
	@Lob
    @Required
    @MaxSize(10000)
    public String description;

	@ManyToMany(mappedBy="followedExpertise") 
	public Set<Employee> followsByEmployees = new HashSet<Employee>();
	
	public Expertise(String name, String description) {
		this.name = name;
		this.description = description;
	}
}

