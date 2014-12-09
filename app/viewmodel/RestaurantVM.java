package viewmodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToOne;

import models.Location;
import models.Restaurant;
import models.RestaurantHours;

public class RestaurantVM {

	public int id;
	public String name;
	public String description;
	public List<MenuVM> menus = new ArrayList<>();
	public List<RestaurantTimeVM> time = new ArrayList<>();
	
}
