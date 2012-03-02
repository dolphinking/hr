package models;

import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Role extends Model {
	
	@Required
	public String name;
	
	public Role(String name){
		this.name = name;
	}
}