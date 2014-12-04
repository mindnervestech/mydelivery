package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class UserAddress extends Model {

	@Id
	private Integer userAddressId;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	private String userAddressLabel;
	private String userAddressHouse;
	private String userAddressStreetName;
	@OneToOne
	@JoinColumn(name="location_id")
	private Location location;
	
	public static Finder<Integer,UserAddress> find = new Finder<>(Integer.class,UserAddress.class);
	
	public Integer getUserAddressId() {
		return userAddressId;
	}
	public void setUserAddressId(Integer userAddressId) {
		this.userAddressId = userAddressId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUserAddressLabel() {
		return userAddressLabel;
	}
	public void setUserAddressLabel(String userAddressLabel) {
		this.userAddressLabel = userAddressLabel;
	}
	public String getUserAddressHouse() {
		return userAddressHouse;
	}
	public void setUserAddressHouse(String userAddressHouse) {
		this.userAddressHouse = userAddressHouse;
	}
	public String getUserAddressStreetName() {
		return userAddressStreetName;
	}
	public void setUserAddressStreetName(String userAddressStreetName) {
		this.userAddressStreetName = userAddressStreetName;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public static UserAddress findByUser(User user) {
		return find.where().eq("user", user).findUnique();
	}
	
}
