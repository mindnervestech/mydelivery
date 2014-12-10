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
	public List<Object> data = new ArrayList<>();
}
