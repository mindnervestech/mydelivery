package viewmodel;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsVM {

	public String username;
	public String firstname;
	public String lastname;
	public String language;
	public String additionalDescription;
	public String email;
	public List<AddressVM> address = new ArrayList<>();
}
