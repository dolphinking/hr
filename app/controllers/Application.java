package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	@Before
	static void checkStatus() {
		List<Job> jobs = Job.find("order by postedDate desc").fetch();
		for(int i = 0; i < jobs.size(); i++) {
			if(jobs.get(i).expiryDate.before(new Date())) {
				jobs.get(i).status = false;
				jobs.get(i).validateAndSave();
			}
		}
	}

	public static void index() {
		List<JobCategory> jobCategories = JobCategory.findAll(); 
		render(jobCategories);
	}

	public static void jobShow(Long id) {
		List<Job> jobs = Job.find("select j from Job j join j.category as c where j.status='TRUE' and c.id = ?", id).fetch();
		JobCategory jobCategory = JobCategory.findById(id);
		render(jobs, jobCategory);
	}
	
	public static void jobDescription(Long id) {
		Job job = Job.findById(id);
		render(job);
	}
}