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
public class MenuCategory extends Model {

	@Id
	public Integer menuCategoryId;
	@OneToOne
	@JoinColumn(name="menu_id")
	public Menu menu;
	public String menuCategoryName;
	public boolean MenuCategoryActive;
	
	public static Finder<Integer,MenuCategory> find = new Finder<>(Integer.class,MenuCategory.class);
	
	public static List<MenuCategory> findByMenuId(Menu menu) {
		return find.where().eq("menu", menu).findList();
	}
	
}
