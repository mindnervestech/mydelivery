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
public class Menu extends Model{

	@Id
	public Integer menuId;
	@OneToOne
	@JoinColumn(name="restaurant_id")
	public Restaurant restaurant;
	public String menuName;
	public Date menuTimeStart;
	public Date menuTimeStop;
	public boolean MenuActive;
	
	public static Finder<Integer,Menu> find = new Finder<>(Integer.class,Menu.class);
	
	public static List<Menu> findByRestaurantId(Restaurant restaurant) {
		return find.where().eq("restaurant", restaurant).findList();
	}
}
