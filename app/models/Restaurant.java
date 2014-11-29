package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Expression;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Restaurant extends Model {
	
	@Id
	public Integer restaurantId;
	public String restaurantName;
	public String restaurantDescription;
	@OneToOne
	@JoinColumn(name="location_id")
	public Location location;
	public String restaurantTag;
	public boolean restaurantPrepaid;
	public float restaurantFloat;
	public boolean restaurantActive;
	
	public static Finder<Integer,Restaurant> find = new Finder<>(Integer.class,Restaurant.class);
	
	public static List<Restaurant> findByTags(List<String> tags) {
		Expression expr = Expr.ilike("restaurantTag",  "%"+tags.get(0)+"%");
		for(int i=1;i<tags.size();i++) {
			expr = Expr.or(Expr.like("restaurantTag",  "%"+tags.get(i)+"%"), expr);
		}
		
		return find.where(expr).findList();
	}
	

	
}
