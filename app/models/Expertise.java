package models;

import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Expertise extends Model {
	
	@Required
	public String name;
	
	@ManyToMany(mappedBy="expertise")
	public List<Applicant> applicant;
	
	public Expertise(String name) {
		this.name = name;
		this.applicant = new ArrayList<Applicant>();
	}
	
	public static Expertise findOrCreateByName(String name) {
        Expertise exp = Expertise.find("byName", name).first();
        if(exp == null) {
            exp = new Expertise(name);
        }
        return exp;
    }
}