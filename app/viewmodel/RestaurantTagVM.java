package viewmodel;

import models.RestaurantTag;

public class RestaurantTagVM {

	public int id;
	public String name;
	public String tag;
	public String description;
	
	public RestaurantTagVM(RestaurantTag restaurantTag) {
		this.id = restaurantTag.getRestaurantTagId();
		this.name = restaurantTag.getRestaurantTagName();
		this.tag = restaurantTag.getRestaurantTagTag();
		this.description = restaurantTag.getRestaurantTagDescription();
	}
}
