package viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class MenuItemVM {

	public int id;
	public String name;
	public double price;
	public List<MenuItemComboVM> combo = new ArrayList<>();
	public List<MenuItemExtraVM> extra = new ArrayList<>();
}
