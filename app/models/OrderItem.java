package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class OrderItem extends Model {

	@Id
	private Integer orderItemId;
	@OneToOne
	@JoinColumn(name="order_id")
	private OrderData order;
	@OneToOne
	@JoinColumn(name="menu_item_id")
	private MenuItem menuItem;
	private boolean orderItemBeverage;
	private String orderItemComboOptions;
	private String orderItemExtraOptions;
	private int orderItemQuantity;
	private String orderItemAdditionalInfo;
	private double orderItemPrice;
	
	public static Finder<Integer,OrderItem> find = new Finder<>(Integer.class,OrderItem.class);
	
	public Integer getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}
	public OrderData getOrder() {
		return order;
	}
	public void setOrder(OrderData order) {
		this.order = order;
	}
	public MenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public boolean isOrderItemBeverage() {
		return orderItemBeverage;
	}
	public void setOrderItemBeverage(boolean orderItemBeverage) {
		this.orderItemBeverage = orderItemBeverage;
	}
	public String getOrderItemComboOptions() {
		return orderItemComboOptions;
	}
	public void setOrderItemComboOptions(String orderItemComboOptions) {
		this.orderItemComboOptions = orderItemComboOptions;
	}
	public String getOrderItemExtraOptions() {
		return orderItemExtraOptions;
	}
	public void setOrderItemExtraOptions(String orderItemExtraOptions) {
		this.orderItemExtraOptions = orderItemExtraOptions;
	}
	public String getOrderItemAdditionalInfo() {
		return orderItemAdditionalInfo;
	}
	public void setOrderItemAdditionalInfo(String orderItemAdditionalInfo) {
		this.orderItemAdditionalInfo = orderItemAdditionalInfo;
	}
	public double getOrderItemPrice() {
		return orderItemPrice;
	}
	public void setOrderItemPrice(double orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}
	public int getOrderItemQuantity() {
		return orderItemQuantity;
	}
	public void setOrderItemQuantity(int orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}
	
	
}
