import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends play.jobs.Job {
 
    public void doJob() {
        // Check if the database is empty
        if(Employee.count() == 0) {
            Fixtures.load("data.yml");
        }
    }
}