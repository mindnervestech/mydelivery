package viewmodel;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class MenuCategoryVM {

	public String name;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public List<MenuItemVM> items;
}
