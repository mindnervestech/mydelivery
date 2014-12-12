package viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class OrderVM {

	public Integer orderId;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public CredentialsVM credentials;
	public Double deliveryFee;
	public Double adminFee;
	public String orderDate;
	public Integer deliveryAddress;
	public List<OrderItemVM> items = new ArrayList<>();
}
