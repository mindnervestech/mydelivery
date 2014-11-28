package controllers;

import java.util.Date;
import java.util.Random;

import org.marre.SmsSender;


import models.Language;
import models.User;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
    	try {
    		Form<LoginForm> form = DynamicForm.form(LoginForm.class).bindFromRequest();
    		String username = form.data().get("username");
    		String password = form.data().get("password");
    		if(username==null || username.isEmpty() || password==null || password.isEmpty()) {
    			return ok(Json.toJson(new ErrorResponse(Error.E202.getCode(), Error.E202.getMessage())));
    		} else {
    			try {
    				Long.parseLong(username);
    				if(username.length()<10) {
    					return ok(Json.toJson(new ErrorResponse(Error.E206.getCode(), Error.E206.getMessage())));
    				} 
    			} catch(NumberFormatException e) {
    				return ok(Json.toJson(new ErrorResponse(Error.E206.getCode(), Error.E206.getMessage())));
    			}
    			Error e = validate(username, password);
    			return ok(Json.toJson(new ErrorResponse(e.getCode(), e.getMessage())));
    		}
    	} catch(Exception e) {
    		return ok(Json.toJson(new ErrorResponse("500",e.getMessage())));
    	} 
    }
    
    public static Result register() {
    	try{
    		Form<RegisterForm> form = DynamicForm.form(RegisterForm.class).bindFromRequest();
    		RegisterForm rForm = form.get();
    		if(rForm.username==null ||
    				rForm.username.isEmpty()||
    				rForm.password==null||
    				rForm.password.isEmpty()||
    				rForm.confirmPassword==null||
    				rForm.confirmPassword.isEmpty()||
    				rForm.email==null||
    				rForm.email.isEmpty()||
    				rForm.firstname==null||
    				rForm.firstname.isEmpty()||
    				rForm.middlename==null||
    				rForm.middlename.isEmpty()||
    				rForm.lastname==null||
    				rForm.lastname.isEmpty()||
    				rForm.language==null||
    				rForm.language.isEmpty()||
    				rForm.communicationEmail==null||
    				rForm.communicationSms==null) {
    			return ok(Json.toJson(new ErrorResponse(Error.E202.getCode(), Error.E202.getMessage())));
    		} else if(!rForm.password.equals(rForm.confirmPassword)){
    			return ok(Json.toJson(new ErrorResponse(Error.E203.getCode(), Error.E203.getMessage())));
    		} else {
    			try {
    				Long.parseLong(rForm.username);
    				if(rForm.username.length()<10) {
    					return ok(Json.toJson(new ErrorResponse(Error.E206.getCode(), Error.E206.getMessage())));
    				} 
    			} catch(NumberFormatException e) {
    				return ok(Json.toJson(new ErrorResponse(Error.E206.getCode(), Error.E206.getMessage())));
    			}
    			if(User.getUserByUserName(rForm.username)!=null) {
    				return ok(Json.toJson(new ErrorResponse(Error.E210.getCode(), Error.E210.getMessage())));
    			} 
    			User user = new User();
    			user.setUserName(rForm.username);
    			user.setUserAdditionalDescription(rForm.additionalDescription);
    			user.setUserCommunicationEmail(rForm.communicationEmail);
    			user.setUserCommunicationSms(rForm.communicationSms);
    			user.setUserEmailAddress(rForm.email);
    			user.setUserFirstname(rForm.firstname);
    			try {
    				user.setUserLanguage(Language.valueOf(rForm.language.toLowerCase()));
    			} catch(IllegalArgumentException e) {
    				return ok(Json.toJson(new ErrorResponse(Error.E205.getCode(), Error.E205.getMessage())));
    			}
    			user.setUserLastname(rForm.lastname);
    			user.setUserPassword(rForm.password);
    			user.setUserRegisterDate(new Date());
    			user.setUserVerificationCode(getRandomCode());
    			user.setUserVerified(false);
    			user.setUserLostPasswordCount(0);
    			user.setUserStatus(false);
    			user.save();
    			// Send SMS with clickatell
    			SmsSender smsSender = SmsSender.getClickatellSender("Mke.manitshana@gmail.com", "ZCFEEACRJDJdQC", "3456360");
    			// The message that you want to send.
    			String msg = "verification code "+user.getUserVerificationCode();
    			// International number to reciever without leading "+"
    			String reciever = user.getUserName();
    			smsSender.connect();
    			String msgids = smsSender.sendTextSms(msg, reciever);
    			smsSender.disconnect();
    			return ok(Json.toJson(new ErrorResponse(Error.E204.getCode(), Error.E204.getMessage())));
    		}
    	} catch(Exception e) {
    		return ok(Json.toJson(new ErrorResponse("500",e.getMessage())));
    	}
    }
    
    public static Result validateUser() {
    	try {
    		ValidateForm form = DynamicForm.form(ValidateForm.class).bindFromRequest().get();
    		if(form.username==null ||
    				form.username.isEmpty() ||
    				form.password==null ||
    				form.password.isEmpty()||
    				form.validationCode==null ||
    				form.validationCode.isEmpty()) {
    			return ok(Json.toJson(new ErrorResponse(Error.E202.getCode(), Error.E202.getMessage())));
    		} else {
    			try {
    				Long.parseLong(form.username);
    				if(form.username.length()<10) {
    					return ok(Json.toJson(new ErrorResponse(Error.E206.getCode(), Error.E206.getMessage())));
    				} 
    			} catch(NumberFormatException e) {
    				return ok(Json.toJson(new ErrorResponse(Error.E206.getCode(), Error.E206.getMessage())));
    			}
    			Error e = validate(form.username, form.password);
    			if(e==Error.E201) {
    				return ok(Json.toJson(new ErrorResponse(Error.E208.getCode(), Error.E208.getMessage())));
    			} else {
    				User u = User.getUserByUserNameAndValidationCode(form.username, form.validationCode);
    				if(u==null) {
    					return ok(Json.toJson(new ErrorResponse(Error.E207.getCode(), Error.E207.getMessage())));
    				} else {
    					u.setUserStatus(true);
    					u.setUserVerified(true);
    					u.update();
    					return ok(Json.toJson(new ErrorResponse(Error.E209.getCode(), Error.E209.getMessage())));
    				}
    			}
    		}
    	} catch(Exception e) {
    		return ok(Json.toJson(new ErrorResponse("500",e.getMessage())));
    	}
    }
    
    public static class ValidateForm {
    	public String username;
    	public String password;
    	public String validationCode;
    }
    
    public static class RegisterForm {
    	public String username;
    	public String password;
    	public String confirmPassword;
    	public String firstname;
    	public String lastname;
    	public String middlename;
    	public String email;
    	public String language;
    	public String additionalDescription;
    	public Boolean communicationSms;
    	public Boolean communicationEmail;
    }
    
    public static class LoginForm {
    	public String username;
    	public String password;
    }
    
    public static class ErrorResponse {
    	public String code;
    	public String message;
    	public ErrorResponse(String code,String message) {
    		this.code = code;
    		this.message = message;
    	}
    }
    
    public enum Error {
    	E201("201","Login Failed!"),
    	E202("202","Required Field Missing!"),
    	E200("200","Login Successful!"),
    	E203("203","Passwords Mismatch!"),
    	E204("204","User Registered Successfully!"),
    	E205("205","Language Param Not Found!"),
    	E206("206","Username is not Valid Number!"),
    	E207("207","Validation Code is Invalid!"),
    	E208("208","Username, Password does'nt matched with our database!"),
    	E209("209","User Validated Successfully!"),
    	E210("210","User Already Exist!");
    	Error(String code,String message) {
    		this.code = code;
    		this.message = message;
    	}
    	
    	private String code;
    	private String message;
		
    	public String getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
    	
    }
    public static String getRandomCode() {
    	Random ran = new Random();
    	
    	int num=0;
    	for(int i=0;i<4;i++) {
    		num = (num*10)+ran.nextInt(10);
    	}
    	return num+"";
    }
    
    public static Error validate(String username,String password) {
    	User user = User.getUserByUserNameAndPassword(username, password);
		if(user==null) {
			return Error.E201;
		}else {
			return Error.E200;
		}
    }
}
