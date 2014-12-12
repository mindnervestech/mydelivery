package viewmodel;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class AddressVM {

	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public Integer addressId;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String addressType;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String username;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String password;
	public String street;
	public String house_bld;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public Integer suburb;
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	public String suburbName;
}
