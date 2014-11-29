package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class RestaurantTag extends Model {
	
	@Id
	private Integer restaurantTagId;
	private String restaurantTagName;
	private String RestaurantTagTag;
	private String RestaurantTagDescription;
	
	public static Finder<Integer,RestaurantTag> find = new Finder<>(Integer.class,RestaurantTag.class);
	
	public static List<RestaurantTag> getAllRestaurantTags() {
		return find.all();
	}
	
	
	public Integer getRestaurantTagId() {
		return restaurantTagId;
	}
	public void setRestaurantTagId(Integer restaurantTagId) {
		this.restaurantTagId = restaurantTagId;
	}
	public String getRestaurantTagName() {
		return restaurantTagName;
	}
	public void setRestaurantTagName(String restaurantTagName) {
		this.restaurantTagName = restaurantTagName;
	}
	public String getRestaurantTagTag() {
		return RestaurantTagTag;
	}
	public void setRestaurantTagTag(String restaurantTagTag) {
		RestaurantTagTag = restaurantTagTag;
	}
	public String getRestaurantTagDescription() {
		return RestaurantTagDescription;
	}
	public void setRestaurantTagDescription(String restaurantTagDescription) {
		RestaurantTagDescription = restaurantTagDescription;
	}
	
}
