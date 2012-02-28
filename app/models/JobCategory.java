package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class JobCategory extends Model {
	
	@Required
	public String name;
	
	public JobCategory(String name) {
		this.name = name;
	}
	
}

