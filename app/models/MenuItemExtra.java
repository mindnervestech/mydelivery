package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class MenuItemExtra extends Model{

	@Id
	private Integer menuItemExtraId;
	@OneToOne
	@JoinColumn(name="restaurant_id")
	private Restaurant restaurant;
	private String menuItemExtraName;
	private double menuItemExtraPrice;
	
	public static Finder<Integer,MenuItemExtra> find = new Finder<>(Integer.class,MenuItemExtra.class);
	
	public Integer getMenuItemExtraId() {
		return menuItemExtraId;
	}
	public void setMenuItemExtraId(Integer menuItemExtraId) {
		this.menuItemExtraId = menuItemExtraId;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public String getMenuItemExtraName() {
		return menuItemExtraName;
	}
	public void setMenuItemExtraName(String menuItemExtraName) {
		this.menuItemExtraName = menuItemExtraName;
	}
	public double getMenuItemExtraPrice() {
		return menuItemExtraPrice;
	}
	public void setMenuItemExtraPrice(double menuItemExtraPrice) {
		this.menuItemExtraPrice = menuItemExtraPrice;
	}
	
	public static MenuItemExtra findById(Integer id) {
		return find.byId(id);
	}
}
