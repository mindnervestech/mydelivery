package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class MenuItemComboOption extends Model {

	@Id
	private Integer menuItemComboOptionId;
	@OneToOne
	@JoinColumn(name="menu_item_combo_id")
	private MenuItemCombo menuItemCombo;
	private String menuItemComboOptionName;
	private double menuItemComboOptionPrice;
	
	public static Finder<Integer,MenuItemComboOption> find = new Finder<>(Integer.class,MenuItemComboOption.class);

	public Integer getMenuItemComboOptionId() {
		return menuItemComboOptionId;
	}

	public void setMenuItemComboOptionId(Integer menuItemComboOptionId) {
		this.menuItemComboOptionId = menuItemComboOptionId;
	}

	public MenuItemCombo getMenuItemCombo() {
		return menuItemCombo;
	}

	public void setMenuItemCombo(MenuItemCombo menuItemCombo) {
		this.menuItemCombo = menuItemCombo;
	}

	public String getMenuItemComboOptionName() {
		return menuItemComboOptionName;
	}

	public void setMenuItemComboOptionName(String menuItemComboOptionName) {
		this.menuItemComboOptionName = menuItemComboOptionName;
	}

	public double getMenuItemComboOptionPrice() {
		return menuItemComboOptionPrice;
	}

	public void setMenuItemComboOptionPrice(double menuItemComboOptionPrice) {
		this.menuItemComboOptionPrice = menuItemComboOptionPrice;
	}
	
	public static List<MenuItemComboOption> findByMenuItemCombo(MenuItemCombo menuItemCombo) {
		return find.where().eq("menuItemCombo", menuItemCombo).findList();
	}
}
