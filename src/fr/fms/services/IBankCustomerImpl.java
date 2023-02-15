package fr.fms.services;
import java.util.HashMap;
import java.util.Map;
import fr.fms.entities.User;

public class IBankCustomerImpl implements IBankCustomer {

	@Override
	public void addCustomer(User user) {

		customers.put(user.getId(), user);
	}

	public Map<Integer, User> customers;
	
	public IBankCustomerImpl() {
		customers = new HashMap();
	}
	
	public void displayCustomer() {
		for(User user : customers.values()) {
			System.out.println(user);
		}
	}
}
