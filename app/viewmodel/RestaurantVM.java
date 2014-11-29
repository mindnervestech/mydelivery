package viewmodel;

import javax.persistence.OneToOne;

import models.Location;
import models.Restaurant;

public class RestaurantVM {

	public int id;
	public String name;
	
	public RestaurantVM(Restaurant restaurant) {
		this.id = restaurant.restaurantId;
		this.name = restaurant.restaurantName;
	}
}
