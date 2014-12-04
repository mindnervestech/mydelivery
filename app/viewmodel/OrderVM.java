package viewmodel;

import java.util.ArrayList;
import java.util.List;

public class OrderVM {

	public CredentialsVM credentials;
	public Double deliveryFee;
	public Double adminFee;
	public String orderDate;
	public List<OrderItemVM> items = new ArrayList<>();
}
