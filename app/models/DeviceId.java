package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class DeviceId extends Model {

	@Id
	public String id;
	
	public static Finder<String,DeviceId> find = new Finder<>(String.class,DeviceId.class);
	
	public static DeviceId findById(String deviceId) {
		return find.byId(deviceId);
	}
	
	public static List<DeviceId> getAll() {
		return find.all();
	}
}
