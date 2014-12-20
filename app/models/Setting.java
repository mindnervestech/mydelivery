package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Setting extends Model {

	@Id
	public Integer settingId;
	public String settingName;
	public String settingValue;
	
	public static Finder<Integer,Setting> find = new Finder<>(Integer.class,Setting.class);
	
	public static Setting getDeliveryFeeDay() {
		return find.where().eq("settingName", "delivery_fee_day").findUnique();
	}
	
	public static Setting getDeliveryFeeNight() {
		return find.where().eq("settingName", "delivery_fee_night").findUnique();
	}
	
	public static Setting getAdminFee() {
		return find.where().eq("settingName", "admin_fee").findUnique();
	}
	
	public static Setting getDeliveryFeeDayRange() {
		return find.where().eq("settingName", "delivery_fee_day_range").findUnique();
	}
	
	public static Setting getDeliveryFeeNightRange() {
		return find.where().eq("settingName", "delivery_fee_night_range").findUnique();
	}
}
