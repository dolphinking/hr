package models;

import javax.persistence.*;

import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class CV extends Model {
	
	public String fileName;
	public Blob file;
	
	@OneToOne
	@Required
	public Applicant applicant;
	
	@Override
	public void _delete() {
	   super._delete();
	   file.getFile().delete();
	}
    
}

