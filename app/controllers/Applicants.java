package controllers;

import play.*;
import play.mvc.*;
import play.libs.*;
import play.cache.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Applicants extends Controller {

	public static void signup() {
		String randomID = Codec.UUID();
		render(randomID);
	}
	
	public static void login() {
		render();
	}
	
	public static void checkUser() {
		
	}
    
	public static void save() {
		render();
	}
	
	public static void captcha(String id) {
        Images.Captcha captcha = Images.captcha();
        String code = captcha.getText("#EEE");
        Cache.set(id, code, "30mn");
        renderBinary(captcha);
    }

}

