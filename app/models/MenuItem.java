package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class MenuItem extends Model {
	
	@Id
	public Integer menuItemId;
	@OneToOne
	@JoinColumn(name="menu_category_id")
	public MenuCategory menuCategory;
	public String menuItemName;
	public String menuItemDescription;
	public double menuItemPrice;
	public boolean MenuItemMainSide;
	public boolean MenuItemSingleCombo;
	public boolean MenuItemSpicy;
	public boolean MenuItemVegetarian;
	public String menuItemDaySchedule;
	public boolean MenuItemTypeId;
	public boolean MenuItemActive;
	
	public static Finder<Integer,MenuItem> find = new Finder<>(Integer.class,MenuItem.class);
	
	public static List<MenuItem> findByMenuCategoryId(MenuCategory menuCategory) {
		return find.where().eq("menuCategory", menuCategory).findList();
	}
}
