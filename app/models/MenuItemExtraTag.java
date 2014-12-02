package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class MenuItemExtraTag extends Model {

	@Id
	private Integer menuItemExtraTagId;
	@OneToOne
	@JoinColumn(name="menu_item_id")
	private MenuItem menuItem;
	@OneToOne
	@JoinColumn(name="menu_item_extra_id")
	private MenuItemExtra menuItemExtra;
	
	public static Finder<Integer,MenuItemExtraTag> find = new Finder<>(Integer.class,MenuItemExtraTag.class);
	
	public Integer getMenuItemExtraTagId() {
		return menuItemExtraTagId;
	}
	public void setMenuItemExtraTagId(Integer menuItemExtraTagId) {
		this.menuItemExtraTagId = menuItemExtraTagId;
	}
	public MenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public MenuItemExtra getMenuItemExtra() {
		return menuItemExtra;
	}
	public void setMenuItemExtra(MenuItemExtra menuItemExtra) {
		this.menuItemExtra = menuItemExtra;
	}
	
	public static List<MenuItemExtraTag> findByMenuItem(MenuItem item) {
		return find.where().eq("menuItem", item).findList();
	}
	
}
