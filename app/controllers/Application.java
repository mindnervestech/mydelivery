package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.marre.SmsSender;


import models.Language;
import models.Location;
import models.Menu;
import models.MenuCategory;
import models.MenuItem;
import models.News;
import models.Restaurant;
import models.RestaurantTag;
import models.User;
import models.UserAddress;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import scala.Array;

import viewmodel.MenuCategoryVM;
import viewmodel.MenuItemVM;
import viewmodel.MenuVM;
import viewmodel.LocationVM;
import viewmodel.ResponseVM;
import viewmodel.RestaurantMenuVM;
import viewmodel.RestaurantTagVM;
import viewmodel.RestaurantVM;
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
    				rForm.street==null||
    				rForm.street.isEmpty()||
    				rForm.house_bld==null||
    				rForm.house_bld.isEmpty()||
    				rForm.suburb==null||
    				rForm.suburb.isEmpty()||
    				rForm.lastname==null||
    				rForm.lastname.isEmpty()||
    				rForm.language==null||
    				rForm.language.isEmpty()||
    				rForm.additionalDescription==null||
    				rForm.additionalDescription.isEmpty()||
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
    			UserAddress userAddress = new UserAddress();
    			userAddress.setUser(user);
    			userAddress.setUserAddressHouse(rForm.house_bld);
    			userAddress.setUserAddressStreetName(rForm.street);
    			userAddress.setLocation(Location.findById(Integer.parseInt(rForm.suburb)));
    			userAddress.setUserAddressLabel("Default Address");
    			userAddress.save();
    			// Send SMS with clickatell
    			/*SmsSender smsSender = SmsSender.getClickatellSender("Mke.manitshana@gmail.com", "ZCFEEACRJDJdQC", "3456360");
    			// The message that you want to send.
    			String msg = "verification code "+user.getUserVerificationCode();
    			// International number to reciever without leading "+"
    			String reciever = user.getUserName();
    			smsSender.connect();
    			String msgids = smsSender.sendTextSms(msg, reciever);
    			smsSender.disconnect();*/
    			return ok(Json.toJson(new ErrorResponse(Error.E200.getCode(), Error.E200.getMessage())));
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
    					return ok(Json.toJson(new ErrorResponse(Error.E200.getCode(), Error.E200.getMessage())));
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
    	public String street;
    	public String house_bld;
    	public String suburb;
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
    
    public static class Tags {
    	public List<String> tags = new ArrayList<>();
    }
    
    public static Result getAllTags() {
    	List<RestaurantTag> list = RestaurantTag.getAllRestaurantTags();
    	List<RestaurantTagVM> vmList = new ArrayList<>();
    	for(RestaurantTag tag: list) {
    		RestaurantTagVM vm = new RestaurantTagVM(tag);
    		vmList.add(vm);
    	}
    	return ok(Json.toJson(vmList));
    }
    
    public static Result getRestaurantsByTags() {
    	Form<Tags> form = DynamicForm.form(Tags.class).bindFromRequest();
    	List<String> tagList = form.get().tags;
    	List<Restaurant> list = Restaurant.findByTags(tagList);
    	List<RestaurantVM> VMs = new ArrayList<>();
    	for(Restaurant restaurant: list) {
    		RestaurantVM restaurantVM = new RestaurantVM(restaurant);
    		VMs.add(restaurantVM);
    	}
    	
    	return ok(Json.toJson(VMs));
    }
    
    public static Result getMenuItems(Integer id) {
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    	Restaurant restaurant= Restaurant.findById(id);
    	
    	RestaurantMenuVM restaurantMenuVM = new RestaurantMenuVM();
    	restaurantMenuVM.id = restaurant.restaurantId;
    	restaurantMenuVM.name = restaurant.restaurantName;
    	List<MenuVM> menuVMList = new ArrayList<>();
    	List<Menu> menuList = Menu.findByRestaurantId(restaurant);
    	for(Menu menu: menuList) {
        	List<MenuCategoryVM> MenuCategoryVMList = new ArrayList<>();
    		MenuVM menuVM = new MenuVM();
    		menuVM.name = menu.menuName;
    		menuVM.from = format.format(menu.menuTimeStart);
    		menuVM.to = format.format(menu.menuTimeStop);
    		
    		List<MenuCategory> menuCategories = MenuCategory.findByMenuId(menu);
    		for(MenuCategory menuCat: menuCategories) {
    			MenuCategoryVM menuCategoryVM = new MenuCategoryVM();
    			menuCategoryVM.name = menuCat.menuCategoryName;
	    			List<MenuItem> menuItems = MenuItem.findByMenuCategoryId(menuCat);
	    			List<MenuItemVM> menuItemNames = new ArrayList<>();
	    			for(MenuItem item: menuItems) {
	    				MenuItemVM itemVM = new MenuItemVM();
	    				itemVM.name = item.menuItemName;
	    				itemVM.price = item.menuItemPrice;
	    				menuItemNames.add(itemVM);
	    				
	    			}
	    				menuCategoryVM.items = menuItemNames;
	    				MenuCategoryVMList.add(menuCategoryVM);
	    				
    		}
    					menuVM.categories = MenuCategoryVMList;
    					menuVMList.add(menuVM);
    					
    	}
    	
    	restaurantMenuVM.menus = menuVMList;
    	
    	return ok(Json.toJson(restaurantMenuVM));
    }
    
    
    public static Result getNewsFeeds() throws ParseException {
    	DynamicForm formData = DynamicForm.form().bindFromRequest();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date fromDate = new Date();
    	Date toDate = new Date();
    	if(formData.get("from") != "") {
    		fromDate = format.parse(formData.get("from"));
    	}
    	if(formData.get("to") != "") {
    		toDate = format.parse(formData.get("to"));
    	}
    	if(formData.get("from") == "" && formData.get("to") !="") {
    		List<News> newsList = News.getNewsByToDate(toDate);
    		for(News news : newsList) {
    			System.out.println(news.getNewsDate());
    		}
    	}
    	if(formData.get("from") != "" && formData.get("to") =="") {
    		
    	}
    	if(formData.get("from") == "" && formData.get("to") =="") {
    		
    	}
    	if(formData.get("from") != "" && formData.get("to") !="") {
    		
    	}
    	return ok();
    }
    
    public static Result getAllLocations() {
    	List<Location> locations = Location.getAll();
    	List<LocationVM> locationVMs = new ArrayList<>();
    	for(Location location: locations) {
    		LocationVM vm = new LocationVM();
    		vm.id = location.getLocationId();
    		vm.name = location.getLocationName();
    		locationVMs.add(vm);
    	}
    	List<Object> objects = new ArrayList<Object>(locationVMs);
    	ResponseVM responseVM = new ResponseVM();
    		try{
    		responseVM.code = "200";
    		responseVM.message = "Locations available";
    		responseVM.data = objects;
    		return ok(Json.toJson(responseVM));
    		} catch(Exception e) {
    			responseVM.code = "211";
        		responseVM.message = e.getMessage();
        		return ok(Json.toJson(responseVM));
    		}
    }
    
}
