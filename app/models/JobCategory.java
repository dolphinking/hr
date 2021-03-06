package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class JobCategory extends Model {

	@Required
	@Column(unique=true)
	public String name;
	
	public JobCategory(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}