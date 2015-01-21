package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class HomeMobile extends Model {

	@Id
	public Integer homeMobileId;
	public String homeMobileSourceEnglish;
	public String homeMobileSourcePortuguese;
	
	public static Finder<Integer,HomeMobile> find = new Finder<>(Integer.class,HomeMobile.class);
	
	public static List<HomeMobile> getAllHomeMobileImages() {
		return find.all();
	}
}
