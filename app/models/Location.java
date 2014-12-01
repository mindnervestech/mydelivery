package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Location extends Model {

	@Id
	@Column(name = "location_id")
	private Integer locationId;
	private String locationName;
	
	public static Finder<Integer,Location> find = new Finder<>(Integer.class,Location.class);
	
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public static List<Location> getAll() {
		return find.all();
	}
	
	public static Location findById(Integer id) {
		return find.byId(id);
	}
}
