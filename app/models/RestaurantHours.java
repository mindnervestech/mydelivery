package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class RestaurantHours extends Model {

	@Id
	private Integer restaurantHoursId;
	@OneToOne
	@JoinColumn(name="restaurant_id")
	private Restaurant restaurant;
	private String restaurantHoursDay;
	private Date restaurantHoursOpen;
	private Date restaurantHoursClose;
	
	public static Finder<Integer,RestaurantHours> find = new Finder<>(Integer.class,RestaurantHours.class);
	
	public Integer getRestaurantHoursId() {
		return restaurantHoursId;
	}
	public void setRestaurantHoursId(Integer restaurantHoursId) {
		this.restaurantHoursId = restaurantHoursId;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getRestaurantHoursDay() {
		return restaurantHoursDay;
	}
	public void setRestaurantHoursDay(String restaurantHoursDay) {
		this.restaurantHoursDay = restaurantHoursDay;
	}
	public Date getRestaurantHoursOpen() {
		return restaurantHoursOpen;
	}
	public void setRestaurantHoursOpen(Date restaurantHoursOpen) {
		this.restaurantHoursOpen = restaurantHoursOpen;
	}
	public Date getRestaurantHoursClose() {
		return restaurantHoursClose;
	}
	public void setRestaurantHoursClose(Date restaurantHoursClose) {
		this.restaurantHoursClose = restaurantHoursClose;
	}
	
	public static List<RestaurantHours> findByRestaurant(Restaurant restaurant) {
		return find.where().eq("restaurant", restaurant).findList();
	}
	
}
