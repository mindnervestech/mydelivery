package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.marre.SmsSender;


import models.Branch;
import models.Language;
import models.Location;
import models.Menu;
import models.MenuCategory;
import models.MenuItem;
import models.MenuItemCombo;
import models.MenuItemComboOption;
import models.MenuItemExtra;
import models.MenuItemExtraTag;
import models.News;
import models.OrderData;
import models.OrderItem;
import models.Restaurant;
import models.RestaurantHours;
import models.RestaurantTag;
import models.User;
import models.UserAddress;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import scala.Array;

import viewmodel.AddressVM;
import viewmodel.MenuCategoryVM;
import viewmodel.MenuItemComboOptionVM;
import viewmodel.MenuItemComboVM;
import viewmodel.MenuItemExtraVM;
import viewmodel.MenuItemVM;
import viewmodel.MenuVM;
import viewmodel.LocationVM;
import viewmodel.NewsVM;
import viewmodel.OrderItemVM;
import viewmodel.OrderVM;
import viewmodel.ResponseVM;
import viewmodel.RestaurantMenuVM;
import viewmodel.RestaurantTagVM;
import viewmodel.RestaurantTimeVM;
import viewmodel.RestaurantVM;
import viewmodel.UserDetailsVM;
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
    	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    	public String password;
    	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    	public String confirmPassword;
    	public String firstname;
    	public String lastname;
    	public String street;
    	public String house_bld;
    	public String suburb;
    	public String email;
    	public String language;
    	public String additionalDescription;
    	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    	public Boolean communicationSms;
    	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
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
    	ResponseVM responseVM = new ResponseVM();
    	try {
    	List<RestaurantTag> list = RestaurantTag.getAllRestaurantTags();
    	if(!list.isEmpty()) {
    	List<RestaurantTagVM> vmList = new ArrayList<>();
    	for(RestaurantTag tag: list) {
    		RestaurantTagVM vm = new RestaurantTagVM(tag);
    		vmList.add(vm);
    	}
	    	List<Object> objects = new ArrayList<Object>(vmList);
	    	responseVM.code = "200";
	    	responseVM.message = "Restaurant tags available";
	    	responseVM.data = objects;
    	} else {
    		responseVM.code = "212";
	    	responseVM.message = "Restaurant tags not available";
    	}
    	} catch(Exception e) {
    		responseVM.code = "211";
        	responseVM.message = "Restaurant tags not available";
    	}
    	return ok(Json.toJson(responseVM));
    }
    
    public static Result getRestaurantsByTags() {
    	Form<Tags> form = DynamicForm.form(Tags.class).bindFromRequest();
    	List<String> tagList = form.get().tags;
    	ResponseVM responseVM = new ResponseVM();
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    	try {
    	List<Restaurant> list = Restaurant.findByTags(tagList);
    	if(!list.isEmpty()) {
    	List<RestaurantVM> VMs = new ArrayList<>();
    	for(Restaurant restaurant: list) {
    		List<RestaurantTimeVM> timeVMList = new ArrayList<>();
    		List<RestaurantHours> restaurantHoursList = RestaurantHours.findByRestaurant(restaurant);
    		RestaurantVM restaurantVM = new RestaurantVM();
    		restaurantVM.id = restaurant.restaurantId;
    		restaurantVM.name = restaurant.restaurantName;
    		restaurantVM.description = restaurant.restaurantDescription;
    		for(RestaurantHours restaurantHours:restaurantHoursList) {
	    		RestaurantTimeVM time = new RestaurantTimeVM();
	    		time.day = restaurantHours.getRestaurantHoursDay();
	    		time.open = format.format(restaurantHours.getRestaurantHoursOpen());
	    		time.close = format.format(restaurantHours.getRestaurantHoursClose());
	    		timeVMList.add(time);
    		}
    		
    		List<Menu> menuList = Menu.findByRestaurantId(restaurant);
    		List<MenuVM> menuVMList = new ArrayList<>();
    		for(Menu menuName: menuList) {
    			MenuVM vm = new MenuVM();
    			vm.name = menuName.menuName;
    			vm.from = format.format(menuName.menuTimeStart);
    			vm.to = format.format(menuName.menuTimeStop);
    			menuVMList.add(vm);
    			List<MenuCategory> menuCategoryList = MenuCategory.findByMenu(menuName);
    			List<MenuCategoryVM> vmList = new ArrayList<>();
    			for(MenuCategory category : menuCategoryList) {
    				MenuCategoryVM categoryVM = new MenuCategoryVM();
    				categoryVM.name = category.menuCategoryName;
    				vmList.add(categoryVM);
    			}
    			vm.categories = vmList;
    		}
    		restaurantVM.menus = menuVMList;
    		restaurantVM.time = timeVMList;
    		VMs.add(restaurantVM);
    	}
	    	List<Object> objects = new ArrayList<Object>(VMs);
	    	responseVM.code = "200";
	    	responseVM.message = "Restaurant available";
	    	responseVM.data = objects;
    	} else {
    		responseVM.code = "212";
	    	responseVM.message = "Restaurant not available";
    	}
    	} catch(Exception e) {
    		responseVM.code = "211";
        	responseVM.message = "Restaurant not available";
    	}
    	return ok(Json.toJson(responseVM));
    }
    
    public static Result getMenuItems(Integer id) {
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    	ResponseVM responseVM = new ResponseVM();
    	RestaurantMenuVM restaurantMenuVM = new RestaurantMenuVM();
    	try {
    	Restaurant restaurant= Restaurant.findById(id);
    	if(restaurant != null) {
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
	    				itemVM.id = item.menuItemId;
	    				itemVM.name = item.menuItemName;
	    				itemVM.price = item.menuItemPrice;
	    				itemVM.description = item.menuItemDescription;
	    				itemVM.isVegetarian = item.MenuItemVegetarian; 
	    				itemVM.isSpicy = item.MenuItemSpicy; 
	    				itemVM.daySchedule = item.menuItemDaySchedule;
	    				List<MenuItemCombo> comboList = MenuItemCombo.findByMenuItem(item);
	    				List<MenuItemComboVM> comboVMList = new ArrayList<>();
	    				for(MenuItemCombo combo: comboList) {
	    					MenuItemComboVM vm = new MenuItemComboVM();
	    					vm.id = combo.getMenuItemComboId();
	    					vm.name = combo.getMenuItemComboName();
	    					List<MenuItemComboOption> comboOptionList = MenuItemComboOption.findByMenuItemCombo(combo);
	    					List<MenuItemComboOptionVM> comboOptionVMList = new ArrayList<>();
	    						for(MenuItemComboOption comboOption: comboOptionList) {
	    							MenuItemComboOptionVM optionVM = new MenuItemComboOptionVM();
	    							optionVM.id = comboOption.getMenuItemComboOptionId();
	    							optionVM.name = comboOption.getMenuItemComboOptionName();
	    							optionVM.price = comboOption.getMenuItemComboOptionPrice();
	    							comboOptionVMList.add(optionVM);
	    						}
	    					vm.options = comboOptionVMList;
	    					comboVMList.add(vm);
	    				}
	    				itemVM.combo = comboVMList;
	    				List<MenuItemExtraVM> menuItemExtraVMList = new ArrayList<>();
	    				List<MenuItemExtraTag> menuExtraList = MenuItemExtraTag.findByMenuItem(item);
		    				for(MenuItemExtraTag tag: menuExtraList) {
		    					MenuItemExtra menuItemExtra = MenuItemExtra.findById(tag.getMenuItemExtra().getMenuItemExtraId());
		    					MenuItemExtraVM extraVM = new MenuItemExtraVM();
		    					extraVM.id = menuItemExtra.getMenuItemExtraId();
		    					extraVM.name = menuItemExtra.getMenuItemExtraName();
		    					extraVM.price = menuItemExtra.getMenuItemExtraPrice();
		    					menuItemExtraVMList.add(extraVM);
		    				}
		    			itemVM.extra = 	menuItemExtraVMList;
	    				
	    				menuItemNames.add(itemVM);
	    				
	    			}
	    				menuCategoryVM.items = menuItemNames;
	    				MenuCategoryVMList.add(menuCategoryVM);
	    				
    		}
    					menuVM.categories = MenuCategoryVMList;
    					menuVMList.add(menuVM);
    					
    	}
    	
    	restaurantMenuVM.menus = menuVMList;
	    	Object object = restaurantMenuVM;
	    	responseVM.code = "200";
	    	responseVM.message = "Menu available";
	    	responseVM.data.add(object);
    	} else {
    		responseVM.code = "212";
	    	responseVM.message = "Menu not available";
    	}
    	} catch(Exception e) {
    		responseVM.code = "211";
    		responseVM.message = e.getMessage();
    	}
    	return ok(Json.toJson(responseVM));
    }
    
    
    public static Result getNewsFeeds() throws ParseException {
    	DynamicForm formData = DynamicForm.form().bindFromRequest();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	ResponseVM responseVM = new ResponseVM();
    	List<News> newsList = new ArrayList<>();
    	Date fromDate = new Date();
    	Date toDate = new Date();
    	if(formData.get("from") != "") {
    		fromDate = format.parse(formData.get("from"));
    	}
    	if(formData.get("to") != "") {
    		toDate = format.parse(formData.get("to"));
    	}
    	try {
    	List<NewsVM> newsVMList = new ArrayList<>();
    	if(formData.get("from") == "" && formData.get("to") !="") {
    		newsList = News.getNewsByToDate(toDate);
    		for(News news : newsList) {
    			NewsVM newsVM = new NewsVM(news);
    			newsVMList.add(newsVM);
    		}
    	}
    	if(formData.get("from") != "" && formData.get("to") =="") {
    		newsList = News.getNewsByFromDate(fromDate);
    		for(News news : newsList) {
    			NewsVM newsVM = new NewsVM(news);
    			newsVMList.add(newsVM);
    		}
    	}
    	if(formData.get("from") == "" && formData.get("to") =="") {
    		newsList = News.getAllNews();
    		for(News news : newsList) {
    			NewsVM newsVM = new NewsVM(news);
    			newsVMList.add(newsVM);
    		}
    	}
    	if(formData.get("from") != "" && formData.get("to") !="") {
    		newsList = News.getNewsBetween(fromDate, toDate);
    		for(News news : newsList) {
    			NewsVM newsVM = new NewsVM(news);
    			newsVMList.add(newsVM);
    		}
    	}
    	if(!newsList.isEmpty()) {
	    	List<Object> objects = new ArrayList<Object>(newsVMList);
	    	responseVM.code = "200";
	    	responseVM.message = "News Available";
	    	responseVM.data = objects;
    	} else {
    		responseVM.code = "212";
	    	responseVM.message = "News not Available";
    	}
    	} catch(Exception e) {
    		responseVM.code = "211";
    		responseVM.message = e.getMessage();
    	}
    	return ok(Json.toJson(responseVM));
    }
    
    public static Result getAllLocations() {
    	ResponseVM responseVM = new ResponseVM();
    	try {
		    	List<Location> locations = Location.getAll();
		if(!locations.isEmpty()) {
		    	List<LocationVM> locationVMs = new ArrayList<>();
		    	for(Location location: locations) {
		    		LocationVM vm = new LocationVM();
		    		vm.id = location.getLocationId();
		    		vm.name = location.getLocationName();
		    		locationVMs.add(vm);
		    	}
		    	List<Object> objects = new ArrayList<Object>(locationVMs);
		    		responseVM.code = "200";
		    		responseVM.message = "Locations available";
		    		responseVM.data = objects;
    	} else {
    		responseVM.code = "212";
    		responseVM.message = "Locations not available";
    	}
    		} catch(Exception e) {
    			responseVM.code = "211";
        		responseVM.message = e.getMessage();
    		}
    	return ok(Json.toJson(responseVM));
    }
    
    
    public static Result getNewsById(Integer id) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	ResponseVM responseVM = new ResponseVM();
    	try{
    	News news = News.findById(id);
    	if( news != null) {
	    	NewsVM vm = new NewsVM();
	    	vm.id = news.getNewsId();
	    	vm.header = news.getNewsHeader();
	    	vm.description = news.getNewsDescription();
	    	vm.image = news.getNewsImage();
	    	vm.date = dateFormat.format(news.getNewsDate());
	    	Object object = vm;
	    	responseVM.code = "200";
	    	responseVM.message = "News available";
	    	responseVM.data.add(object);
    	} else {
    		responseVM.code = "212";
    		responseVM.message = "No news available";
    	}
    	
    	} catch(Exception e) {
    		responseVM.code = "211";
    		responseVM.message = e.getMessage();
    	}
    	return ok(Json.toJson(responseVM));
    }
    
    public static Result saveOrder() throws ParseException {
    	Form<OrderVM> form = DynamicForm.form(OrderVM.class).bindFromRequest();
    	OrderVM orderVM = form.get();
    	ResponseVM responseVM = new ResponseVM();
    	try {
    		
	    	User user = User.getUserByUserNameAndPassword(orderVM.credentials.username, orderVM.credentials.password);
	    	if(user == null) {
	    		responseVM.code = "211";
	    		responseVM.message = "Invalid User";
	    	} else {
	    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    		String dateComplete = "9999-1-1";
	    		UserAddress userAddress = UserAddress.findById(orderVM.deliveryAddress);
	    		Branch branch = Branch.findByName("Branch 2");
	    		OrderData order = new OrderData();
	    		order.setUser(user);
	    		order.setUserAddress(userAddress);
	    		order.setBranch(branch);
	    		order.setOrderDeliveryFee(orderVM.deliveryFee);
	    		order.setOrderAdminFee(orderVM.adminFee);
	    		order.setOrderDateStart(format.parse(orderVM.orderDate));
	    		order.setOrderDateComplete(format.parse(dateComplete));
	    		order.setOrderNote(" ");
	    		order.save();
	    		for(OrderItemVM itemVM : orderVM.items) {
	    			OrderItem orderItem = new OrderItem();
	    			orderItem.setOrder(order);
	    			orderItem.setMenuItem(MenuItem.findById(itemVM.id));
	    			orderItem.setOrderItemBeverage(false);
	    			orderItem.setOrderItemComboOptions(itemVM.combo);
	    			orderItem.setOrderItemExtraOptions(itemVM.extra);
	    			orderItem.setOrderItemPrice(itemVM.price);
	    			orderItem.setOrderItemQuantity(itemVM.quantity);
	    			orderItem.setOrderItemAdditionalInfo(itemVM.additionalInformation);
	    			orderItem.save();
	    		}
	    		responseVM.code = "200";
	    		responseVM.message = "Order Saved Successfully!";
	    		responseVM.orderId = order.getOrderId();
	    	}
    	}catch(Exception e) {
    		responseVM.code = "212";
    		responseVM.message = e.getMessage();
    	}
    	return ok(Json.toJson(responseVM));
    }
    
    public static Result getUserDetails(String id) {
    	UserDetailsVM vm = new UserDetailsVM();
    	ResponseVM responseVM = new ResponseVM();
    	User user = User.getUserByUserName(id);
    	try {
    	if(user == null) {
    		responseVM.code = "211";
    		responseVM.message ="User not available";
    	} else {
	    	List<UserAddress> userAddressList = UserAddress.findByUser(user);
	    	List<AddressVM> vmList = new ArrayList();
	    	vm.username = user.getUserName();
	    	vm.firstname = user.getUserFirstname();
	    	vm.lastname = user.getUserLastname();
	    	vm.language = user.getUserLanguage().name();
	    	vm.additionalDescription = user.getUserAdditionalDescription();
	    	vm.email = user.getUserEmailAddress();
	    	for(UserAddress address: userAddressList) {
	    		AddressVM addressVM = new AddressVM();
	    		addressVM.house_bld = address.getUserAddressHouse();
	    		addressVM.street = address.getUserAddressStreetName();
	    		addressVM.suburbName = address.getLocation().getLocationName();
	    		vmList.add(addressVM);
	    	}
	    	vm.address = vmList;
	    	responseVM.code = "200";
    		responseVM.message ="User available";
    		responseVM.data.add(vm);
    	}
    	} catch(Exception e) {
    		responseVM.code = "212";
    		responseVM.message = e.getMessage();
    	}
    	return ok(Json.toJson(responseVM));
    }
    
    public static Result addAddress() {
    	ResponseVM responseVM = new ResponseVM();
    	try {
	    	Form<AddressVM> form = DynamicForm.form(AddressVM.class).bindFromRequest();
	    	AddressVM addressVM = form.get();
	    	User user = User.getUserByUserNameAndPassword(addressVM.username, addressVM.password);
	    	if(user == null) {
	    		responseVM.code = "211";
	    		responseVM.message = "Invalid User";
	    	} else {
		    	UserAddress userAddress = new UserAddress();
		    	userAddress.setUserAddressLabel("Default Address");
		    	userAddress.setUser(user);
		    	userAddress.setUserAddressHouse(addressVM.house_bld);
		    	userAddress.setUserAddressStreetName(addressVM.street);
		    	userAddress.setLocation(Location.findById(addressVM.suburb));
		    	userAddress.save();
		    	responseVM.code = "200";
	    		responseVM.message = "Address Saved Successfully!";
	    	}
    	} catch(Exception e) {
    		responseVM.code = "212";
    		responseVM.message = e.getMessage();
    	}
    	return ok(Json.toJson(responseVM));
    }
    
}
