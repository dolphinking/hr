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
	
	public Expertise(String name, String description) {
		this.name = name;
		this.description = description;
	}
}

