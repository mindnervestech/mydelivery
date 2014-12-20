package viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class ResponseVM {

	public String code;
	public String message;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public Integer orderId;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public Integer defaultAddress;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String delivery_fee_day;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String delivery_fee_night;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String admin_fee;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String delivery_fee_day_range;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String delivery_fee_night_range;
	public List<Object> data = new ArrayList<>();
}
