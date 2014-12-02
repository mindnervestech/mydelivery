package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class MenuItemCombo extends Model {

	@Id
	private Integer menuItemComboId;
	@OneToOne
	@JoinColumn(name="menu_item_id")
	private MenuItem menuItem;
	private String menuItemComboName;
	
	public static Finder<Integer,MenuItemCombo> find = new Finder<>(Integer.class,MenuItemCombo.class);

	public Integer getMenuItemComboId() {
		return menuItemComboId;
	}

	public void setMenuItemComboId(Integer menuItemComboId) {
		this.menuItemComboId = menuItemComboId;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public String getMenuItemComboName() {
		return menuItemComboName;
	}

	public void setMenuItemComboName(String menuItemComboName) {
		this.menuItemComboName = menuItemComboName;
	}
	
	public static List<MenuItemCombo> findByMenuItem(MenuItem menuItem) {
		return find.where().eq("menuItem", menuItem).findList();
	}
}
