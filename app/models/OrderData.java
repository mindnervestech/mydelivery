package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class OrderData extends Model {

	@Id
	private Integer orderId;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	@OneToOne
	@JoinColumn(name="user_address_id")
	private UserAddress userAddress;
	@OneToOne
	@JoinColumn(name="branch_id")
	private Branch branch;
	private double orderDeliveryFee;
	private double orderAdminFee;
	private Date orderDateStart;
	private Date orderDateComplete;
	private int driverId;
	private int agentId;
	private boolean orderStatus;
	private double orderFloatTaken;
	private double orderCashReturned;
	private String orderNote;
	private boolean orderSource;
	
	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public double getOrderDeliveryFee() {
		return orderDeliveryFee;
	}

	public void setOrderDeliveryFee(double orderDeliveryFee) {
		this.orderDeliveryFee = orderDeliveryFee;
	}

	public double getOrderAdminFee() {
		return orderAdminFee;
	}

	public void setOrderAdminFee(double orderAdminFee) {
		this.orderAdminFee = orderAdminFee;
	}

	public Date getOrderDateStart() {
		return orderDateStart;
	}

	public void setOrderDateStart(Date orderDateStart) {
		this.orderDateStart = orderDateStart;
	}

	public Date getOrderDateComplete() {
		return orderDateComplete;
	}

	public void setOrderDateComplete(Date orderDateComplete) {
		this.orderDateComplete = orderDateComplete;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public boolean isOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getOrderFloatTaken() {
		return orderFloatTaken;
	}

	public void setOrderFloatTaken(double orderFloatTaken) {
		this.orderFloatTaken = orderFloatTaken;
	}

	public double getOrderCashReturned() {
		return orderCashReturned;
	}

	public void setOrderCashReturned(double orderCashReturned) {
		this.orderCashReturned = orderCashReturned;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public boolean isOrderSource() {
		return orderSource;
	}

	public void setOrderSource(boolean orderSource) {
		this.orderSource = orderSource;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static Finder<Integer,OrderData> find = new Finder<>(Integer.class,OrderData.class);
	
}
