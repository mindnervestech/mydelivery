package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Branch extends Model {

	@Id
	private Integer branchId;
	private String branchName;
	private String branchRegion;
	
	public static Finder<Integer,Branch> find = new Finder<>(Integer.class,Branch.class);
	
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchRegion() {
		return branchRegion;
	}
	public void setBranchRegion(String branchRegion) {
		this.branchRegion = branchRegion;
	}
	
	public static Branch findByName(String branchName) {
		return find.where().eq("branchName", branchName).findUnique();
	}
	
}
